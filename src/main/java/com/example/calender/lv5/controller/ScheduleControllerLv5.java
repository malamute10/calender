package com.example.calender.lv5.controller;

import com.example.calender.lv5.dto.schedule.ScheduleCreationRequestDtoLv5;
import com.example.calender.lv5.dto.schedule.SchedulePutRequestDtoLv5;
import com.example.calender.lv5.dto.schedule.ScheduleResponseDtoLv5;
import com.example.calender.lv5.service.ScheduleServiceLv5;
import com.example.calender.lv5.service.UserServiceLv5;
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
public class ScheduleControllerLv5 {

    private final UserServiceLv5 userService;
    private final ScheduleServiceLv5 scheduleService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ScheduleResponseDtoLv5 createSchedule(@RequestBody ScheduleCreationRequestDtoLv5 creationDto) {

        userService.verifyUserExists(creationDto.getUserId());

        return scheduleService.create(creationDto);
    }

    @GetMapping()
    public List<ScheduleResponseDtoLv5> search(@RequestParam(required = false) Integer userId,
                                               @RequestParam(required = false) LocalDate updatedDate,
                                               @RequestParam(defaultValue = "0") int pageNumber,
                                               @RequestParam(defaultValue = "10") int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return scheduleService.getAll(userId, updatedDate, pageable);
    }

    @GetMapping("/{id}")
    public ScheduleResponseDtoLv5 getScheduleResponseDtoById(@PathVariable Integer id) {
        return scheduleService.getResponseDtoById(id);
    }

    @PutMapping("/{id}")
    public ScheduleResponseDtoLv5 putById(@PathVariable Integer id,
                                          @RequestBody SchedulePutRequestDtoLv5 putRequestDto) {
        return scheduleService.putById(id, putRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id, @RequestParam String password) {

        scheduleService.deleteById(id, password);
    }
}
