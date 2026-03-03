package com.aparzero.maya.controller;


import com.aparzero.maya.domain.ResponseObj;
import com.aparzero.maya.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<ResponseObj> getUserInfo(@RequestHeader("X-User-Id") int userId){
      log.info("Fetching User Info: {}",userId);
      return userService.getUserInfo(userId);
    }
    @GetMapping("/friends")
    public ResponseEntity<ResponseObj> getUserFriendList(@RequestHeader("X-User-Id") int userId){
        log.info("Fetching User FriendList: {}",userId);
        return userService.getUserFriendsList(userId);
    }
}
