package com.example.calender.lv4.service;

import com.example.calender.lv4.dto.user.UserDtoLv4;
import com.example.calender.lv4.entity.UserLv4;
import com.example.calender.lv4.repository.UserRepositoryLv4;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceLv4 {

    private final UserRepositoryLv4 userRepository;

    @Transactional(readOnly = true)
    public void verifyUserExists(int id) {
        getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void patchName(int id, String name) {
        userRepository.updateNameById(id, name);
    }

    @Transactional(readOnly = true)
    public UserDtoLv4 getUserDtoById(int id) {
        return new UserDtoLv4(getById(id));
    }

    private UserLv4 getById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found User"));
    }
}
