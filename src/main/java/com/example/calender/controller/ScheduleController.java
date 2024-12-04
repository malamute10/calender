package com.example.calender.controller;

import com.example.calender.dto.ScheduleCreationRequestDto;

import com.example.calender.dto.ScheduleResponseDto;
import com.example.calender.service.ScheduleService;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/schedules")
@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleCreationRequestDto creationDto) {
        return scheduleService.create(creationDto);
    }

    @GetMapping()
    public List<ScheduleResponseDto> search(@RequestParam(required = false) String author,
                                            @RequestParam(required = false) LocalDate updatedDate) {

        LocalDateTime startUpdatedDatetime = null;
        LocalDateTime endUpdatedDatetime = null;

        if(updatedDate != null) {
            startUpdatedDatetime = updatedDate.atStartOfDay();
            endUpdatedDatetime = updatedDate.plusDays(1).atStartOfDay();
        }

        return scheduleService.getAll(author, startUpdatedDatetime, endUpdatedDatetime);
    }
}
