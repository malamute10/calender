package com.example.calender.controller;

import com.example.calender.dto.ScheduleCreationRequestDto;

import com.example.calender.dto.ScheduleResponseDto;
import com.example.calender.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/schedules")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleCreationRequestDto creationDto) {
        return scheduleService.create(creationDto);
    }
}
