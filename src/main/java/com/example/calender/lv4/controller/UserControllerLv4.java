package com.example.calender.lv4.controller;

import com.example.calender.lv4.dto.user.UserDtoLv4;
import com.example.calender.lv4.dto.user.UserPatchRequestDtoLv4;
import com.example.calender.lv4.service.UserServiceLv4;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/lv4/users")
@RestController
@RequiredArgsConstructor
public class UserControllerLv4 {

    private final UserServiceLv4 userService;

    @PatchMapping("/{id}")
    public UserDtoLv4 patchName(@PathVariable int id, @RequestBody UserPatchRequestDtoLv4 patchRequestDto) {

        userService.patchName(id, patchRequestDto.getName());

        return userService.getUserDtoById(id);
    }
}
