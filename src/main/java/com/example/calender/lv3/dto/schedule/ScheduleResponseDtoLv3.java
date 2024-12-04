package com.example.calender.lv3.dto.schedule;

import com.example.calender.lv3.dto.user.UserDtoLv3;
import com.example.calender.lv3.entity.ScheduleLv3;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ScheduleResponseDtoLv3 {

    private int id;
    private int userId;
    private String content;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;

    public ScheduleResponseDtoLv3(ScheduleLv3 schedule) {
        this.id = schedule.getId();
        this.userId = schedule.getUserId();
        this.content = schedule.getContent();
        this.createdDatetime = schedule.getCreatedDatetime();
        this.updatedDatetime = schedule.getUpdatedDatetime();
    }
}
