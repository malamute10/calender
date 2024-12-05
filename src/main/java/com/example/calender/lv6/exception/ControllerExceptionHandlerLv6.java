package com.example.calender.lv6.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandlerLv6 {

    @ExceptionHandler(ApiExceptionLv6.class)
    public ResponseEntity<ErrorResponseLv6> handleApiException(ApiExceptionLv6 e) {

        HttpStatus status = e.getStatus();

        return new ResponseEntity<>(
                new ErrorResponseLv6(status.value(), e.getMessage()),
                status
        );
    }
}