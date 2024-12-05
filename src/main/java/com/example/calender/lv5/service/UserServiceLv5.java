package com.example.calender.lv5.service;

import com.example.calender.lv5.dto.user.UserDtoLv5;
import com.example.calender.lv5.entity.UserLv5;
import com.example.calender.lv5.exception.ApiException;
import com.example.calender.lv5.repository.UserRepositoryLv5;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceLv5 {

    private final UserRepositoryLv5 userRepository;

    @Transactional(readOnly = true)
    public void verifyUserExists(int id) {
        getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void patchName(int id, String name) {
        userRepository.updateNameById(id, name);
    }

    @Transactional(readOnly = true)
    public UserDtoLv5 getUserDtoById(int id) {
        return new UserDtoLv5(getById(id));
    }

    private UserLv5 getById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Not Found User"));
    }
}
