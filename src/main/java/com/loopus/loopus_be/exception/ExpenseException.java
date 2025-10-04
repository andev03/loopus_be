package com.loopus.loopus_be.exception;

public class ExpenseException extends RuntimeException {
    public ExpenseException(String message){
        super(message);
    }
}