package com.dfc.network.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class DfcControllerAdvice {


    @ExceptionHandler({CustomMessageException.class})
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleCustomMessageException(CustomMessageException cme) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(cme.getStatus().value());
        errorResponse.setError(cme.getStatus().getReasonPhrase());
        errorResponse.setMessage(cme.getMessage());
        return new ResponseEntity<>(errorResponse, cme.getStatus());
    }
}
