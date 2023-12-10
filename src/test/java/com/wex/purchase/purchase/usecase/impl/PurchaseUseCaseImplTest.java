package com.wex.purchase.purchase.usecase.impl;

import com.wex.purchase.purchase.entity.PurchaseDocument;
import com.wex.purchase.purchase.entrypoint.http.data.request.PurchaseRequest;
import com.wex.purchase.purchase.repository.PurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;


class PurchaseUseCaseImplTest {

    @InjectMocks
    private PurchaseUseCaseImpl target;

    @Mock
    private PurchaseRepository purchaseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void makePurchase() {
        //GIVEN
        PurchaseRequest purchaseRequest = new PurchaseRequest();
        purchaseRequest.setPurchaseAmount("$1,000.00");
        purchaseRequest.setTransactionDate("2023-01-01T21:21:21");
        purchaseRequest.setDescription("Playstaion 5");

        PurchaseDocument purchaseDocument = PurchaseDocument.builder()
                .id("12345")
                .build();
        //WHEN
        Mockito.when(purchaseRepository.save(Mockito.any())).thenReturn(purchaseDocument);
        //THEN
        target.makePurchase(purchaseRequest);
        Mockito.verify(purchaseRepository, Mockito.times(1)).save(Mockito.any());
    }
}