package com.wex.purchase.purchase.usecase;

import com.wex.purchase.purchase.entrypoint.http.data.request.PurchaseRequest;

public interface PurchaseUseCase {

    String makePurchase(PurchaseRequest purchaseRequest);
}
