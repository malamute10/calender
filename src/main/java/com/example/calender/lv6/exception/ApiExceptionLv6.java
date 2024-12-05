package com.example.calender.lv6.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ApiExceptionLv6 extends RuntimeException {

    private HttpStatus status;
    private String message;

}
