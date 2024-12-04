package com.example.calender.lv4.service;

import com.example.calender.lv4.dto.schedule.ScheduleCreationRequestDtoLv4;
import com.example.calender.lv4.dto.schedule.SchedulePutRequestDtoLv4;
import com.example.calender.lv4.dto.schedule.ScheduleResponseDtoLv4;
import com.example.calender.lv4.entity.ScheduleLv4;
import com.example.calender.lv4.repository.ScheduleRepositoryLv4;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceLv4 {

    private final ScheduleRepositoryLv4 scheduleRepository;

    @Transactional(rollbackFor = Exception.class)
    public ScheduleResponseDtoLv4 create(ScheduleCreationRequestDtoLv4 creationDto) {

        ScheduleLv4 schedule = creationDto.toEntity();

        ScheduleLv4 savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDtoLv4(savedSchedule);
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDtoLv4> getAll(Integer userId, LocalDate updatedDate, Pageable pageable) {

        return scheduleRepository.findAll(userId, updatedDate, pageable).stream()
                .map(ScheduleResponseDtoLv4::new)
                .toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public ScheduleResponseDtoLv4 putById(int id, SchedulePutRequestDtoLv4 putRequestDto) {

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
    public ScheduleResponseDtoLv4 getResponseDtoById(int id) {

        return new ScheduleResponseDtoLv4(getById(id));
    }

    @Transactional(readOnly = true)
    public ScheduleLv4 getById(int id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }
}
