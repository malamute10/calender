package com.example.calender.lv6.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponseLv6 {

    private int status;
    private String message;

}
