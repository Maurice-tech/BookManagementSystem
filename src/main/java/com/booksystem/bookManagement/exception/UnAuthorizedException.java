package com.booksystem.bookManagement.exception;

import lombok.Getter;

@Getter
public class UnAuthorizedException extends RuntimeException{
    private final String customMessage;

    public UnAuthorizedException(String customMessage) {
        this.customMessage = customMessage;
    }
}
