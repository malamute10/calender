package com.example.calender.lv3.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLv3 {
    private int id;
    private String name;
    private String email;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;


    public UserLv3(int id) {
        this.id = id;
    }
}
