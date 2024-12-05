package com.example.calender.lv6.controller;

import com.example.calender.lv6.dto.user.UserDtoLv6;
import com.example.calender.lv6.dto.user.UserPatchRequestDtoLv6;
import com.example.calender.lv6.service.UserServiceLv6;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/lv5/users")
@RestController
@RequiredArgsConstructor
public class UserControllerLv6 {

    private final UserServiceLv6 userService;

    @PatchMapping("/{id}")
    public UserDtoLv6 patchName(@PathVariable int id, @RequestBody UserPatchRequestDtoLv6 patchRequestDto) {

        userService.patchName(id, patchRequestDto.getName());

        return userService.getUserDtoById(id);
    }
}
