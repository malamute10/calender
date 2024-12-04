package com.example.calender.lv3.dto.schedule;

import com.example.calender.lv3.entity.ScheduleLv3;
import lombok.Getter;

@Getter
public class ScheduleCreationRequestDtoLv3 {

    private int userId;
    private String password;
    private String content;


    public ScheduleLv3 toEntity() {
        return new ScheduleLv3(this.userId, this.password, this.content);
    }
}
