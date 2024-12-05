package com.example.calender.lv6.service;

import com.example.calender.lv6.dto.schedule.ScheduleCreationRequestDtoLv6;
import com.example.calender.lv6.dto.schedule.SchedulePutRequestDtoLv6;
import com.example.calender.lv6.dto.schedule.ScheduleResponseDtoLv6;
import com.example.calender.lv6.entity.ScheduleLv6;
import com.example.calender.lv6.exception.ApiExceptionLv6;
import com.example.calender.lv6.repository.ScheduleRepositoryLv6;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceLv6 {

    private final ScheduleRepositoryLv6 scheduleRepository;

    @Transactional(rollbackFor = Exception.class)
    public ScheduleResponseDtoLv6 create(ScheduleCreationRequestDtoLv6 creationDto) {

        ScheduleLv6 schedule = creationDto.toEntity();

        ScheduleLv6 savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDtoLv6(savedSchedule);
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDtoLv6> getAll(Integer userId, LocalDate updatedDate, Pageable pageable) {

        return scheduleRepository.findAll(userId, updatedDate, pageable).stream()
                .map(ScheduleResponseDtoLv6::new)
                .toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public ScheduleResponseDtoLv6 putById(int id, SchedulePutRequestDtoLv6 putRequestDto) {

        String savedPassword = getById(id).getPassword();
        verifyPasswordEquality(savedPassword, putRequestDto.getPassword());

        scheduleRepository.updateContentById(id, putRequestDto.getContent());

        return getResponseDtoById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(int id, String password) {

        String savedPassword = getById(id).getPassword();
        verifyPasswordEquality(savedPassword, password);

        scheduleRepository.deleteById(id);
    }

    private void verifyPasswordEquality(String savedPassword, String inputPassword) {
        if(!savedPassword.equals(inputPassword)) {
            throw new ApiExceptionLv6(HttpStatus.BAD_REQUEST, "Password is incorrect");
        }
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDtoLv6 getResponseDtoById(int id) {

        return new ScheduleResponseDtoLv6(getById(id));
    }

    @Transactional(readOnly = true)
    public ScheduleLv6 getById(int id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ApiExceptionLv6(HttpStatus.NOT_FOUND, "Schedule not found"));
    }
}
