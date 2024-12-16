package com.example.calender.lv4.dto.schedule;

import com.example.calender.lv4.entity.ScheduleLv4;
import lombok.Getter;

@Getter
public class ScheduleCreationRequestDtoLv4 {

    private int userId;
    private String password;
    private String content;
    private String title2;


    public ScheduleLv4 toEntity() {
        return new ScheduleLv4(this.userId, this.password, this.content);
    }
}
