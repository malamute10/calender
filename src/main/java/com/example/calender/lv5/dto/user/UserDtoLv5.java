package com.example.calender.lv5.dto.user;

import com.example.calender.lv5.entity.UserLv5;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UserDtoLv5 {

    private int id;
    private String name;
    private String email;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;

    public UserDtoLv5(UserLv5 user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.createdDatetime = user.getCreatedDatetime();
        this.updatedDatetime = user.getUpdatedDatetime();
    }
}
