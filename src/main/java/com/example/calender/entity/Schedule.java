package com.example.calender.entity;


import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Schedule {

    private int id;
    private String author;
    private String password;
    private String content;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;
}
