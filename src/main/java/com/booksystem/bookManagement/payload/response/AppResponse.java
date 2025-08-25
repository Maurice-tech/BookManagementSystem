package com.booksystem.bookManagement.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private int status;

    public static <T> AppResponse<T> success(String message, T data, int status) {
        return new AppResponse<>(true, message, data, status);
    }

    public static <T> AppResponse<T> failure(String message, int status) {
        return new AppResponse<>(false, message, null, status);
    }

    public static <T> AppResponse<T> error(String message, int status) {
        return new AppResponse<>(false, message, null, status);
    }
}

