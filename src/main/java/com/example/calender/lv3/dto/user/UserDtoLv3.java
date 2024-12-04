package com.example.calender.lv3.dto.user;

import com.example.calender.lv3.entity.UserLv3;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UserDtoLv3 {

    private int id;
    private String name;
    private String email;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;

    public UserDtoLv3(UserLv3 user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.createdDatetime = user.getCreatedDatetime();
        this.updatedDatetime = user.getUpdatedDatetime();
    }
}
