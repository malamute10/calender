package com.example.calender.lv5.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException e) {

        HttpStatus status = e.getStatus();

        return new ResponseEntity<>(
                new ErrorResponse(status.value(), e.getMessage()),
                status
        );
    }
}
