package com.example.calender.lv5.controller;

import com.example.calender.lv5.dto.user.UserDtoLv5;
import com.example.calender.lv5.dto.user.UserPatchRequestDtoLv5;
import com.example.calender.lv5.service.UserServiceLv5;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/lv5/users")
@RestController
@RequiredArgsConstructor
public class UserControllerLv5 {

    private final UserServiceLv5 userService;

    @PatchMapping("/{id}")
    public UserDtoLv5 patchName(@PathVariable int id, @RequestBody UserPatchRequestDtoLv5 patchRequestDto) {

        userService.patchName(id, patchRequestDto.getName());

        return userService.getUserDtoById(id);
    }
}
