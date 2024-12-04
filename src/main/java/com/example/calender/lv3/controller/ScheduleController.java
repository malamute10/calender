package com.example.calender.lv3.controller;

import com.example.calender.lv3.dto.ScheduleCreationRequestDto;
import com.example.calender.lv3.dto.SchedulePutRequestDto;
import com.example.calender.lv3.dto.ScheduleResponseDto;
import com.example.calender.lv3.service.ScheduleService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/{id}")
    public ScheduleResponseDto getScheduleResponseDtoById(@PathVariable Integer id) {
        return scheduleService.getResponseDtoById(id);
    }

    @PutMapping("/{id}")
    public ScheduleResponseDto putById(@PathVariable Integer id,
                                       @RequestBody SchedulePutRequestDto putRequestDto) {
        return scheduleService.putById(id, putRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id, @RequestParam String password) {

        scheduleService.deleteById(id, password);
    }
}
