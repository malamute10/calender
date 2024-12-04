package com.example.calender.lv3.entity;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Schedule {

    private int id;
    private User user;
    private String password;
    private String content;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;


    public Schedule(User user, String password, String content) {
        this.user = user;
        this.password = password;
        this.content = content;
    }
}
