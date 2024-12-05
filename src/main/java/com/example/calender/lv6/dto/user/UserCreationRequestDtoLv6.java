package com.example.calender.lv6.dto.user;

import com.example.calender.lv6.entity.UserLv6;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserCreationRequestDtoLv6 {

    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;


    public UserLv6 toEntity() {
        return new UserLv6(this.name, this.email);
    }
}
