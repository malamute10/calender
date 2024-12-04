package com.example.calender.lv4.dto.schedule;

import com.example.calender.lv4.dto.user.UserDtoLv4;
import com.example.calender.lv4.entity.ScheduleLv4;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ScheduleResponseDtoLv4 {

    private int id;
    private UserDtoLv4 user;
    private String content;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;

    public ScheduleResponseDtoLv4(ScheduleLv4 schedule) {
        this.id = schedule.getId();
        this.user = new UserDtoLv4(schedule.getUser());
        this.content = schedule.getContent();
        this.createdDatetime = schedule.getCreatedDatetime();
        this.updatedDatetime = schedule.getUpdatedDatetime();
    }
}
