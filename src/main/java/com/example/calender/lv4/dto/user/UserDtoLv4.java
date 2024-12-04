package com.example.calender.lv4.dto.user;

import com.example.calender.lv4.entity.UserLv4;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UserDtoLv4 {

    private int id;
    private String name;
    private String email;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;

    public UserDtoLv4(UserLv4 user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.createdDatetime = user.getCreatedDatetime();
        this.updatedDatetime = user.getUpdatedDatetime();
    }
}
