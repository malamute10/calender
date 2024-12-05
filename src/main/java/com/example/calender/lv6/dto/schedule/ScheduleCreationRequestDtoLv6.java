package com.example.calender.lv6.dto.schedule;

import com.example.calender.lv6.entity.ScheduleLv6;
import lombok.Getter;

@Getter
public class ScheduleCreationRequestDtoLv6 {

    private int userId;
    private String password;
    private String content;


    public ScheduleLv6 toEntity() {
        return new ScheduleLv6(this.userId, this.password, this.content);
    }
}
