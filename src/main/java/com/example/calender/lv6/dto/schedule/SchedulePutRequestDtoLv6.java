package com.example.calender.lv6.dto.schedule;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class SchedulePutRequestDtoLv6 {

    @NotNull
    private String password;

    @NotNull
    @Length(max = 200)
    private String content;
}
