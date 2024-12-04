package com.example.calender.lv3.service;

import com.example.calender.lv3.dto.schedule.ScheduleCreationRequestDtoLv3;
import com.example.calender.lv3.dto.schedule.SchedulePutRequestDtoLv3;
import com.example.calender.lv3.dto.schedule.ScheduleResponseDtoLv3;
import com.example.calender.lv3.entity.ScheduleLv3;
import com.example.calender.lv3.repository.ScheduleRepositoryLv3;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceLv3 {

    private final ScheduleRepositoryLv3 scheduleRepository;

    @Transactional(rollbackFor = Exception.class)
    public ScheduleResponseDtoLv3 create(ScheduleCreationRequestDtoLv3 creationDto) {

        ScheduleLv3 schedule = creationDto.toEntity();

        ScheduleLv3 savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDtoLv3(savedSchedule);
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDtoLv3> getAll(String author, LocalDateTime startUpdatedDatetime, LocalDateTime endUpdatedDatetime) {

        return scheduleRepository.findAll(author, startUpdatedDatetime, endUpdatedDatetime).stream()
                .map(ScheduleResponseDtoLv3::new)
                .toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public ScheduleResponseDtoLv3 putById(int id, SchedulePutRequestDtoLv3 putRequestDto) {

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
    public ScheduleResponseDtoLv3 getResponseDtoById(int id) {

        return new ScheduleResponseDtoLv3(getById(id));
    }

    @Transactional(readOnly = true)
    public ScheduleLv3 getById(int id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }
}
