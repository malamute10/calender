package com.example.calender.lv4.dto.schedule;

import com.example.calender.lv4.entity.ScheduleLv4;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ScheduleResponseDtoLv4 {

    private int id;
    private int userId;
    private String author;
    private String content;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;

    public ScheduleResponseDtoLv4(ScheduleLv4 schedule) {
        this.id = schedule.getId();
        this.userId = schedule.getUser().getId();
        this.author = schedule.getUser().getName();
        this.content = schedule.getContent();
        this.createdDatetime = schedule.getCreatedDatetime();
        this.updatedDatetime = schedule.getUpdatedDatetime();
    }
}
