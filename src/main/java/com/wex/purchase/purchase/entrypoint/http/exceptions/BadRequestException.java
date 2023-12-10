package com.wex.purchase.purchase.entrypoint.http.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
