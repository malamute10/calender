package com.example.calender.required.dto;

import com.example.calender.required.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleCreationRequestDto {

    private String author;
    private String password;
    private String content;


    public Schedule toEntity() {
        return new Schedule(this.author, this.password, this.content);
    }
}
