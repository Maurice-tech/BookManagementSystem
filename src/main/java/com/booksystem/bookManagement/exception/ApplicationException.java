package com.booksystem.bookManagement.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private final String customMessage;

    public ApplicationException(String customMessage) {
        super(customMessage);
        this.customMessage = customMessage;
    }

}

