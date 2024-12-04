package com.example.calender.lv3.controller;

import com.example.calender.lv3.dto.user.UserDtoLv3;
import com.example.calender.lv3.dto.user.UserPatchRequestDto;
import com.example.calender.lv3.service.UserServiceLv3;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/lv3/users")
@RestController
@RequiredArgsConstructor
public class UserControllerLv3 {

    private final UserServiceLv3 userService;

    @PatchMapping("/{id}")
    public UserDtoLv3 patchName(@PathVariable int id, @RequestBody UserPatchRequestDto patchRequestDto) {

        userService.patchName(id, patchRequestDto.getName());

        return userService.getUserDtoById(id);
    }
}
