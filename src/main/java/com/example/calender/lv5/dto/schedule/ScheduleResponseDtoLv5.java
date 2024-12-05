package com.example.calender.lv5.dto.schedule;

import com.example.calender.lv5.entity.ScheduleLv5;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ScheduleResponseDtoLv5 {

    private int id;
    private int userId;
    private String author;
    private String content;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;

    public ScheduleResponseDtoLv5(ScheduleLv5 schedule) {
        this.id = schedule.getId();
        this.userId = schedule.getUser().getId();
        this.author = schedule.getUser().getName();
        this.content = schedule.getContent();
        this.createdDatetime = schedule.getCreatedDatetime();
        this.updatedDatetime = schedule.getUpdatedDatetime();
    }
}
