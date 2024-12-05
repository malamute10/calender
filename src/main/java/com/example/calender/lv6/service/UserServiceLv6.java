package com.example.calender.lv6.service;

import com.example.calender.lv6.dto.user.UserDtoLv6;
import com.example.calender.lv6.entity.UserLv6;
import com.example.calender.lv6.exception.ApiExceptionLv6;
import com.example.calender.lv6.repository.UserRepositoryLv6;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceLv6 {

    private final UserRepositoryLv6 userRepository;

    @Transactional(readOnly = true)
    public void verifyUserExists(int id) {
        getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void patchName(int id, String name) {
        userRepository.updateNameById(id, name);
    }

    @Transactional(readOnly = true)
    public UserDtoLv6 getUserDtoById(int id) {
        return new UserDtoLv6(getById(id));
    }

    private UserLv6 getById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ApiExceptionLv6(HttpStatus.NOT_FOUND, "Not Found User"));
    }
}
