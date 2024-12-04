package com.example.calender.lv3.entity;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleLv3 {

    private int id;
    private UserLv3 user;
    private String password;
    private String content;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;


    public ScheduleLv3(int userId, String password, String content) {
        this.user = new UserLv3(userId);
        this.password = password;
        this.content = content;
    }

    public ScheduleLv3(UserLv3 user, String password, String content) {
        this.user = user;
        this.password = password;
        this.content = content;
    }
}
