package com.example.calender.lv3.service;

import com.example.calender.lv3.dto.user.UserDtoLv3;
import com.example.calender.lv3.entity.UserLv3;
import com.example.calender.lv3.repository.UserRepositoryLv3;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceLv3 {

    private final UserRepositoryLv3 userRepository;

    @Transactional(readOnly = true)
    public void verifyUserExists(int id) {
        getById(id);
    }

    private UserLv3 getById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found User"));
    }
}
