package com.wex.purchase.purchase.usecase.exceptions;

public class PurchaseGenericError extends RuntimeException {

    public PurchaseGenericError(String message) {
        super(message);
    }

    public PurchaseGenericError(Throwable cause) {
        super(cause);
    }
}
