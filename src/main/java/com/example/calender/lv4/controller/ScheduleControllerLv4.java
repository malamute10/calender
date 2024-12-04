package com.example.calender.lv4.controller;

import com.example.calender.lv4.dto.schedule.ScheduleCreationRequestDtoLv4;
import com.example.calender.lv4.dto.schedule.SchedulePutRequestDtoLv4;
import com.example.calender.lv4.dto.schedule.ScheduleResponseDtoLv4;
import com.example.calender.lv4.service.ScheduleServiceLv4;
import com.example.calender.lv4.service.UserServiceLv4;
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

@RequestMapping("/lv4/schedules")
@RestController
@RequiredArgsConstructor
public class ScheduleControllerLv4 {

    private final UserServiceLv4 userService;
    private final ScheduleServiceLv4 scheduleService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ScheduleResponseDtoLv4 createSchedule(@RequestBody ScheduleCreationRequestDtoLv4 creationDto) {

        userService.verifyUserExists(creationDto.getUserId());

        return scheduleService.create(creationDto);
    }

    @GetMapping()
    public List<ScheduleResponseDtoLv4> search(@RequestParam(required = false) String author,
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
    public ScheduleResponseDtoLv4 getScheduleResponseDtoById(@PathVariable Integer id) {
        return scheduleService.getResponseDtoById(id);
    }

    @PutMapping("/{id}")
    public ScheduleResponseDtoLv4 putById(@PathVariable Integer id,
                                          @RequestBody SchedulePutRequestDtoLv4 putRequestDto) {
        return scheduleService.putById(id, putRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id, @RequestParam String password) {

        scheduleService.deleteById(id, password);
    }
}
