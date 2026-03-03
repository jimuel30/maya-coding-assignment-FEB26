package com.aparzero.maya.service;


import com.aparzero.maya.exception.NotFoundException;
import com.aparzero.maya.domain.ResponseObj;
import com.aparzero.maya.dto.UserDto;
import com.aparzero.maya.model.User;
import com.aparzero.maya.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public ResponseEntity<ResponseObj> getUserInfo(int userId){
        User user = validateUserId(userId);
        log.info("User Exists: {}",userId);
        return ResponseObj.success(UserDto.convert(user));
    }

    public User validateUserId(int userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User Not Found"));
    }

}
