package com.example.calender.lv6.dto.schedule;

import com.example.calender.lv6.entity.ScheduleLv6;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class ScheduleCreationRequestDtoLv6 {

    @NotNull
    private Integer userId;

    @NotNull
    private String password;

    @NotNull
    @Length(max = 200)
    private String content;


    public ScheduleLv6 toEntity() {
        return new ScheduleLv6(this.userId, this.password, this.content);
    }
}
