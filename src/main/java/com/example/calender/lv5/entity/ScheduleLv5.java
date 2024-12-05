package com.example.calender.lv5.entity;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleLv5 {

    private int id;
    private UserLv5 user;
    private String password;
    private String content;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;


    public ScheduleLv5(int userId, String password, String content) {
        this.user = new UserLv5(userId);
        this.password = password;
        this.content = content;
    }

    public ScheduleLv5(UserLv5 user, String password, String content) {
        this.user = user;
        this.password = password;
        this.content = content;
    }
}
