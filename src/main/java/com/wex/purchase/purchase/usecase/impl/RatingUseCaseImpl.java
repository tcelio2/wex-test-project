package com.wex.purchase.purchase.usecase.impl;

import com.wex.purchase.purchase.entity.PurchaseDocument;
import com.wex.purchase.purchase.entrypoint.http.data.request.PurchaseRequest;
import com.wex.purchase.purchase.entrypoint.http.data.request.RatingRequest;
import com.wex.purchase.purchase.entrypoint.http.data.response.RatingResponse;
import com.wex.purchase.purchase.entrypoint.http.exceptions.BadRequestException;
import com.wex.purchase.purchase.gateway.TreasuryReportingRateGateway;
import com.wex.purchase.purchase.gateway.data.response.DataTreasuryRateResponse;
import com.wex.purchase.purchase.gateway.data.response.TreasuryRateResponse;
import com.wex.purchase.purchase.repository.PurchaseRepository;
import com.wex.purchase.purchase.usecase.RatingUseCase;
import com.wex.purchase.purchase.utils.CurrencyUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RatingUseCaseImpl implements RatingUseCase {

    private static final Logger LOGGER = LogManager.getLogger(RatingUseCaseImpl.class);
    private final TreasuryReportingRateGateway treasuryReportingRateGateway;
    private final PurchaseRepository purchaseRepository;

    @Override
    public List<RatingResponse> retrieveRate(RatingRequest ratingRequest) {

        Optional<PurchaseDocument> purchaseDocument = purchaseRepository.findById(ratingRequest.getTrackingId());
        if (purchaseDocument.isEmpty()) {
            throw new BadRequestException("Purchase not found using this ID");
        }
        Map<String, String> mapRequest = new HashMap<>();
        mapRequest.put("currency", ratingRequest.getCurrency());
        mapRequest.put("country", ratingRequest.getCountry());
        mapRequest.put("record_date", purchaseDocument.get().getTransactionDate().toLocalDate().toString());
        TreasuryRateResponse treasuryRateResponse = treasuryReportingRateGateway.getTreasuryRate(mapRequest);

        filteringMoreThanOneTreasury(ratingRequest.getCurrency(), ratingRequest.getCountry(), treasuryRateResponse);

        List<RatingResponse> ratingResponseList = new ArrayList<>();

        treasuryRateResponse.getData().forEach(c -> {
            RatingResponse ratingResponse = RatingResponse.builder()
                    .trackingId(purchaseDocument.get().getId())
                    .exchangeRate(c.getExchangeRate())
                    .country(c.getCountry())
                    .countryCurrencyDesc(c.getCountryCurrencyDesc())
                    .currency(c.getCurrency())
                    .effectiveDate(c.getEffectiveDate())
                    .purchaseAmount(calculatePurchaseAmount(purchaseDocument.get().getPurchaseAmount(), c.getExchangeRate()))
                    .purchaseAmountInDollar(purchaseDocument.get().getPurchaseAmount())
                    .purchaseDate(purchaseDocument.get().getTransactionDate().toString())
                    .recordCalendarDay(c.getRecordCalendarDay())
                    .recordCalendarMonth(c.getRecordCalendarMonth())
                    .recordCalendarYear(c.getRecordCalendarYear())
                    .recordCalendarQuarter(c.getRecordCalendarQuarter())
                    .recordFiscalQuarter(c.getRecordFiscalQuarter())
                    .recordDate(c.getRecordDate())
                    .exchangeRate(c.getExchangeRate())
                    .recordFiscalYear(c.getRecordFiscalYear())
                    .build();
            ratingResponseList.add(ratingResponse);
        });

        return ratingResponseList;
    }

    private TreasuryRateResponse filteringMoreThanOneTreasury(String currency, String country, TreasuryRateResponse treasuryRateResponse) {
        if (isMoreThanOneTreasury(currency, country, treasuryRateResponse.getData())) {
            int size = treasuryRateResponse.getData().size();
            Map<DataTreasuryRateResponse, LocalDate> dataTreasuryLocalDateMap = new HashMap<>();
            treasuryRateResponse.getData().forEach(c -> {
                int day = Integer.parseInt(c.getRecordCalendarDay());
                int month = Integer.parseInt(c.getRecordCalendarMonth());
                int year = Integer.parseInt(c.getRecordCalendarYear());
                LocalDate localDate = LocalDate.of(year, month, day);

                dataTreasuryLocalDateMap.put(c, localDate);
            });

            Map.Entry<DataTreasuryRateResponse, LocalDate> first = dataTreasuryLocalDateMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .skip(size - 1)
                    .findFirst().get();

            treasuryRateResponse.setData(Arrays.asList(first.getKey()));
        }
        return treasuryRateResponse;
    }

    private Boolean isMoreThanOneTreasury(String currency, String country, List<DataTreasuryRateResponse> data) {
        LOGGER.info("Verifying if list of treasury is bigger than one using both currency and country");
        return StringUtils.isNotBlank(currency) && StringUtils.isNotBlank(country)
                && CollectionUtils.isNotEmpty(data) && data.size() > 1;
    }

    private BigDecimal calculatePurchaseAmount(BigDecimal purchaseDollar, String rating) {
        try {
            BigDecimal parsedToBigDecimal = CurrencyUtils.parseToBigDecimal(rating, Locale.US);
            return parsedToBigDecimal.multiply(purchaseDollar);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
