package com.dfc.network.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomMessageException extends Exception{
    private final HttpStatus status;
    private final String message;

    public CustomMessageException(HttpStatus status, String message) {
        this.message = message;
        this.status = status;
    }
}
