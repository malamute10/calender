package com.example.calender.lv3.entity;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleLv3 {

    private int id;
    private int userId;
    private String password;
    private String content;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;


    public ScheduleLv3(int userId, String password, String content) {
        this.userId = userId;
        this.password = password;
        this.content = content;
    }
}
