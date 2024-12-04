package com.example.calender.lv4.entity;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleLv4 {

    private int id;
    private UserLv4 user;
    private String password;
    private String content;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;


    public ScheduleLv4(int userId, String password, String content) {
        this.user = new UserLv4(userId);
        this.password = password;
        this.content = content;
    }

    public ScheduleLv4(UserLv4 user, String password, String content) {
        this.user = user;
        this.password = password;
        this.content = content;
    }
}
