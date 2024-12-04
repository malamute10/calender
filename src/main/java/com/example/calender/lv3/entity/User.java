package com.example.calender.lv3.entity;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class User {
    private int id;
    private String name;
    private String email;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;
}
