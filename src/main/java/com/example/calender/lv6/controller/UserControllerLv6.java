package com.example.calender.lv6.controller;

import com.example.calender.lv6.dto.user.UserCreationRequestDtoLv6;
import com.example.calender.lv6.dto.user.UserDtoLv6;
import com.example.calender.lv6.dto.user.UserPatchRequestDtoLv6;
import com.example.calender.lv6.service.UserServiceLv6;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/lv6/users")
@RestController
@RequiredArgsConstructor
public class UserControllerLv6 {

    private final UserServiceLv6 userService;

    @PatchMapping("/{id}")
    public UserDtoLv6 patchName(@PathVariable int id, @Valid  @RequestBody UserPatchRequestDtoLv6 patchRequestDto) {

        userService.patchName(id, patchRequestDto.getName());

        return userService.getUserDtoById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDtoLv6 create(@Valid @RequestBody UserCreationRequestDtoLv6 creationDto) {
        return userService.create(creationDto);
    }
}
