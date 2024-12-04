package com.example.calender.lv3.controller;

import com.example.calender.lv3.dto.schedule.ScheduleCreationRequestDtoLv3;
import com.example.calender.lv3.dto.schedule.SchedulePutRequestDtoLv3;
import com.example.calender.lv3.dto.schedule.ScheduleResponseDtoLv3;
import com.example.calender.lv3.service.ScheduleServiceLv3;
import com.example.calender.lv3.service.UserServiceLv3;
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

@RequestMapping("/lv3/schedules")
@RestController
@RequiredArgsConstructor
public class ScheduleControllerLv3 {

    private final UserServiceLv3 userService;
    private final ScheduleServiceLv3 scheduleService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ScheduleResponseDtoLv3 createSchedule(@RequestBody ScheduleCreationRequestDtoLv3 creationDto) {

        userService.verifyUserExists(creationDto.getUserId());

        return scheduleService.create(creationDto);
    }

    @GetMapping()
    public List<ScheduleResponseDtoLv3> search(@RequestParam(required = false) String author,
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
    public ScheduleResponseDtoLv3 getScheduleResponseDtoById(@PathVariable Integer id) {
        return scheduleService.getResponseDtoById(id);
    }

    @PutMapping("/{id}")
    public ScheduleResponseDtoLv3 putById(@PathVariable Integer id,
                                          @RequestBody SchedulePutRequestDtoLv3 putRequestDto) {
        return scheduleService.putById(id, putRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id, @RequestParam String password) {

        scheduleService.deleteById(id, password);
    }
}
