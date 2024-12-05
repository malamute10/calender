package com.example.calender.lv6.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLv6 {
    private int id;
    private String name;
    private String email;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;


    public UserLv6(int id) {
        this.id = id;
    }

    public UserLv6(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
