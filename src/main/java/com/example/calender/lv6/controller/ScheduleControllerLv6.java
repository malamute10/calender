package com.example.calender.lv6.controller;

import com.example.calender.lv6.dto.schedule.ScheduleCreationRequestDtoLv6;
import com.example.calender.lv6.dto.schedule.SchedulePutRequestDtoLv6;
import com.example.calender.lv6.dto.schedule.ScheduleResponseDtoLv6;
import com.example.calender.lv6.service.ScheduleServiceLv6;
import com.example.calender.lv6.service.UserServiceLv6;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

@RequestMapping("/lv5/schedules")
@RestController
@RequiredArgsConstructor
public class ScheduleControllerLv6 {

    private final UserServiceLv6 userService;
    private final ScheduleServiceLv6 scheduleService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ScheduleResponseDtoLv6 createSchedule(@RequestBody ScheduleCreationRequestDtoLv6 creationDto) {

        userService.verifyUserExists(creationDto.getUserId());

        return scheduleService.create(creationDto);
    }

    @GetMapping()
    public List<ScheduleResponseDtoLv6> search(@RequestParam(required = false) Integer userId,
                                               @RequestParam(required = false) LocalDate updatedDate,
                                               @RequestParam(defaultValue = "0") int pageNumber,
                                               @RequestParam(defaultValue = "10") int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return scheduleService.getAll(userId, updatedDate, pageable);
    }

    @GetMapping("/{id}")
    public ScheduleResponseDtoLv6 getScheduleResponseDtoById(@PathVariable Integer id) {
        return scheduleService.getResponseDtoById(id);
    }

    @PutMapping("/{id}")
    public ScheduleResponseDtoLv6 putById(@PathVariable Integer id,
                                          @RequestBody SchedulePutRequestDtoLv6 putRequestDto) {
        return scheduleService.putById(id, putRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id, @RequestParam String password) {

        scheduleService.deleteById(id, password);
    }
}
