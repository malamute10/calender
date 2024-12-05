package com.example.calender.lv6.entity;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleLv6 {

    private int id;
    private UserLv6 user;
    private String password;
    private String content;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;


    public ScheduleLv6(int userId, String password, String content) {
        this.user = new UserLv6(userId);
        this.password = password;
        this.content = content;
    }

    public ScheduleLv6(UserLv6 user, String password, String content) {
        this.user = user;
        this.password = password;
        this.content = content;
    }
}
