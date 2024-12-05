package com.example.calender.lv5.dto.schedule;

import com.example.calender.lv5.entity.ScheduleLv5;
import lombok.Getter;

@Getter
public class ScheduleCreationRequestDtoLv5 {

    private int userId;
    private String password;
    private String content;


    public ScheduleLv5 toEntity() {
        return new ScheduleLv5(this.userId, this.password, this.content);
    }
}
