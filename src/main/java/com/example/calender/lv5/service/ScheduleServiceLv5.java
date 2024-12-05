package com.example.calender.lv5.service;

import com.example.calender.lv5.dto.schedule.ScheduleCreationRequestDtoLv5;
import com.example.calender.lv5.dto.schedule.SchedulePutRequestDtoLv5;
import com.example.calender.lv5.dto.schedule.ScheduleResponseDtoLv5;
import com.example.calender.lv5.entity.ScheduleLv5;
import com.example.calender.lv5.exception.ApiException;
import com.example.calender.lv5.repository.ScheduleRepositoryLv5;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.DispatcherServlet;

@Service
@RequiredArgsConstructor
public class ScheduleServiceLv5 {

    private final ScheduleRepositoryLv5 scheduleRepository;

    @Transactional(rollbackFor = Exception.class)
    public ScheduleResponseDtoLv5 create(ScheduleCreationRequestDtoLv5 creationDto) {

        ScheduleLv5 schedule = creationDto.toEntity();

        ScheduleLv5 savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDtoLv5(savedSchedule);
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDtoLv5> getAll(Integer userId, LocalDate updatedDate, Pageable pageable) {

        return scheduleRepository.findAll(userId, updatedDate, pageable).stream()
                .map(ScheduleResponseDtoLv5::new)
                .toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public ScheduleResponseDtoLv5 putById(int id, SchedulePutRequestDtoLv5 putRequestDto) {

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
            throw new RuntimeException("Password is incorrect");
        }
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDtoLv5 getResponseDtoById(int id) {

        return new ScheduleResponseDtoLv5(getById(id));
    }

    @Transactional(readOnly = true)
    public ScheduleLv5 getById(int id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Schedule not found"));
    }
}
