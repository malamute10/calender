package com.example.calender.required.service;

import com.example.calender.required.dto.ScheduleCreationRequestDto;
import com.example.calender.required.dto.SchedulePutRequestDto;
import com.example.calender.required.dto.ScheduleResponseDto;
import com.example.calender.required.entity.Schedule;
import com.example.calender.required.repository.ScheduleRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional(rollbackFor = Exception.class)
    public ScheduleResponseDto create(ScheduleCreationRequestDto creationDto) {

        Schedule schedule = creationDto.toEntity();

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule);
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getAll(String author, LocalDateTime startUpdatedDatetime, LocalDateTime endUpdatedDatetime) {

        return scheduleRepository.findAll(author, startUpdatedDatetime, endUpdatedDatetime).stream()
                .map(ScheduleResponseDto::new)
                .toList();
    }

    @Transactional(rollbackFor = Exception.class)
    public ScheduleResponseDto putById(int id, SchedulePutRequestDto putRequestDto) {

        String savedPassword = getById(id).getPassword();
        verifyPasswordEquality(savedPassword, putRequestDto.getPassword());

        scheduleRepository.updateAuthorAndContentById(id, putRequestDto.getAuthor(), putRequestDto.getContent());

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
    public ScheduleResponseDto getResponseDtoById(int id) {

        return new ScheduleResponseDto(getById(id));
    }

    @Transactional(readOnly = true)
    public Schedule getById(int id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }
}
