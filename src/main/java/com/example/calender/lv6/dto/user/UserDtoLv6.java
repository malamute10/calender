package com.example.calender.lv6.dto.user;

import com.example.calender.lv6.entity.UserLv6;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UserDtoLv6 {

    private int id;
    private String name;
    private String email;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;

    public UserDtoLv6(UserLv6 user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.createdDatetime = user.getCreatedDatetime();
        this.updatedDatetime = user.getUpdatedDatetime();
    }
}
