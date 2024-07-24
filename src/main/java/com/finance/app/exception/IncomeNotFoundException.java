package com.finance.app.exception;

public class IncomeNotFoundException extends RuntimeException{
    public IncomeNotFoundException(String message) {
        super(message);
    }
}
