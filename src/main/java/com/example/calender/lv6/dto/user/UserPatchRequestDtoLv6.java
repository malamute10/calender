package com.example.calender.lv6.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserPatchRequestDtoLv6 {

    @NotNull
    private String name;
}
