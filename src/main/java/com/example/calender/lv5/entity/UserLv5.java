package com.example.calender.lv5.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLv5 {
    private int id;
    private String name;
    private String email;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;


    public UserLv5(int id) {
        this.id = id;
    }
}
