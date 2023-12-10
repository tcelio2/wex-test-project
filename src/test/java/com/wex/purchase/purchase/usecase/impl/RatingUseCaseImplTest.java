package com.wex.purchase.purchase.usecase.impl;

import com.wex.purchase.purchase.entity.PurchaseDocument;
import com.wex.purchase.purchase.entrypoint.http.data.request.RatingRequest;
import com.wex.purchase.purchase.entrypoint.http.data.response.RatingResponse;
import com.wex.purchase.purchase.gateway.TreasuryReportingRateGateway;
import com.wex.purchase.purchase.gateway.data.response.DataTreasuryRateResponse;
import com.wex.purchase.purchase.gateway.data.response.TreasuryRateResponse;
import com.wex.purchase.purchase.repository.PurchaseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RatingUseCaseImplTest {

    @InjectMocks
    private RatingUseCaseImpl target;

    @Mock
    private RatingUseCaseImpl targetMock;
    @Mock
    private TreasuryReportingRateGateway treasuryReportingRateGateway;
    @Mock
    private PurchaseRepository purchaseRepository;

    @Captor
    private ArgumentCaptor<List<DataTreasuryRateResponse>> dataListCapture;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void retrieveRate_sucess_one_row() {
        //GIVEN
        RatingRequest ratingRequest = new RatingRequest();
        ratingRequest.setCountry("Brazil");
        ratingRequest.setCurrency("Dollar");
        ratingRequest.setTrackingId("111222333");


        DataTreasuryRateResponse dataTreasuryRateResponse1 = new DataTreasuryRateResponse();
        dataTreasuryRateResponse1.setRecordCalendarYear("2023");
        dataTreasuryRateResponse1.setRecordCalendarMonth("2");
        dataTreasuryRateResponse1.setRecordCalendarDay("22");
        dataTreasuryRateResponse1.setExchangeRate("1.2");

        DataTreasuryRateResponse dataTreasuryRateResponse2 = new DataTreasuryRateResponse();
        dataTreasuryRateResponse2.setRecordCalendarYear("2024");
        dataTreasuryRateResponse2.setRecordCalendarMonth("2");
        dataTreasuryRateResponse2.setRecordCalendarDay("10");
        dataTreasuryRateResponse2.setExchangeRate("1.3");

        DataTreasuryRateResponse dataTreasuryRateResponse3 = new DataTreasuryRateResponse();
        dataTreasuryRateResponse3.setRecordCalendarYear("2023");
        dataTreasuryRateResponse3.setRecordCalendarMonth("1");
        dataTreasuryRateResponse3.setRecordCalendarDay("23");
        dataTreasuryRateResponse3.setExchangeRate("1.7");

        TreasuryRateResponse treasuryRateResponse = new TreasuryRateResponse();
        treasuryRateResponse.setData(Arrays.asList(dataTreasuryRateResponse1, dataTreasuryRateResponse2, dataTreasuryRateResponse3));


        PurchaseDocument purchaseDocument = new PurchaseDocument();
        purchaseDocument.setId("111222333");
        purchaseDocument.setPurchaseAmount(BigDecimal.valueOf(2700.00));
        purchaseDocument.setTransactionDate(LocalDateTime.now().minusDays(2));

        //WHEN
        Mockito.when(purchaseRepository.findById(Mockito.any())).thenReturn(Optional.of(purchaseDocument));
        Mockito.when(treasuryReportingRateGateway.getTreasuryRate(Mockito.any())).thenReturn(treasuryRateResponse);
        //THEN
        List<RatingResponse> ratingResponseList = target.retrieveRate(ratingRequest);
        Assertions.assertNotNull(ratingResponseList);
        Assertions.assertEquals(1, ratingResponseList.size());
        Assertions.assertEquals(dataTreasuryRateResponse2.getExchangeRate(), ratingResponseList.get(0).getExchangeRate());
        Assertions.assertEquals(dataTreasuryRateResponse2.getRecordCalendarYear(), ratingResponseList.get(0).getRecordCalendarYear());
        Assertions.assertEquals(dataTreasuryRateResponse2.getRecordCalendarMonth(), ratingResponseList.get(0).getRecordCalendarMonth());
        Assertions.assertEquals(dataTreasuryRateResponse2.getRecordCalendarDay(), ratingResponseList.get(0).getRecordCalendarDay());
    }

    @Test
    void retrieveRate_sucess_more_than_one_row() {
        //GIVEN
        RatingRequest ratingRequest = new RatingRequest();
        ratingRequest.setCountry(null);//not specify country --- may return more than one row
        ratingRequest.setCurrency("Dollar");
        ratingRequest.setTrackingId("111222333");

        DataTreasuryRateResponse dataTreasuryRateResponse1 = new DataTreasuryRateResponse();
        dataTreasuryRateResponse1.setRecordCalendarYear("2023");
        dataTreasuryRateResponse1.setRecordCalendarMonth("2");
        dataTreasuryRateResponse1.setRecordCalendarDay("22");
        dataTreasuryRateResponse1.setExchangeRate("1.2");

        DataTreasuryRateResponse dataTreasuryRateResponse2 = new DataTreasuryRateResponse();
        dataTreasuryRateResponse2.setRecordCalendarYear("2024");
        dataTreasuryRateResponse2.setRecordCalendarMonth("2");
        dataTreasuryRateResponse2.setRecordCalendarDay("10");
        dataTreasuryRateResponse2.setExchangeRate("1.3");

        DataTreasuryRateResponse dataTreasuryRateResponse3 = new DataTreasuryRateResponse();
        dataTreasuryRateResponse3.setRecordCalendarYear("2023");
        dataTreasuryRateResponse3.setRecordCalendarMonth("1");
        dataTreasuryRateResponse3.setRecordCalendarDay("23");
        dataTreasuryRateResponse3.setExchangeRate("1.7");

        TreasuryRateResponse treasuryRateResponse = new TreasuryRateResponse();
        treasuryRateResponse.setData(Arrays.asList(dataTreasuryRateResponse1, dataTreasuryRateResponse2, dataTreasuryRateResponse3));


        PurchaseDocument purchaseDocument = new PurchaseDocument();
        purchaseDocument.setId("111222333");
        purchaseDocument.setPurchaseAmount(BigDecimal.valueOf(2700.00));
        purchaseDocument.setTransactionDate(LocalDateTime.now().minusDays(2));

        //WHEN
        Mockito.when(purchaseRepository.findById(Mockito.any())).thenReturn(Optional.of(purchaseDocument));
        Mockito.when(treasuryReportingRateGateway.getTreasuryRate(Mockito.any())).thenReturn(treasuryRateResponse);
        //THEN
        List<RatingResponse> ratingResponseList = target.retrieveRate(ratingRequest);
        Assertions.assertNotNull(ratingResponseList);
        Assertions.assertEquals(3, ratingResponseList.size());
//        Assertions.assertEquals(dataTreasuryRateResponse2.getExchangeRate(), ratingResponseList.get(0).getExchangeRate());
//        Assertions.assertEquals(dataTreasuryRateResponse2.getRecordCalendarYear(), ratingResponseList.get(0).getRecordCalendarYear());
//        Assertions.assertEquals(dataTreasuryRateResponse2.getRecordCalendarMonth(), ratingResponseList.get(0).getRecordCalendarMonth());
//        Assertions.assertEquals(dataTreasuryRateResponse2.getRecordCalendarDay(), ratingResponseList.get(0).getRecordCalendarDay());
    }

}