package com.aparzero.maya.service;

import com.aparzero.maya.model.User;
import com.aparzero.maya.repo.UserRepository;
import com.aparzero.maya.util.ComputationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.*;


@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
    private final JsonPlaceholderService jsonPlaceholderService;
    private final UserRepository userRepository;

    public void generateUser(){
        log.info("GENERATING USERS: ...");
        List<User> userList = userRepository.findAll();
        List<User> existingUsers = new ArrayList<>();
        if(userList.isEmpty()){
           userList = jsonPlaceholderService.getUsers();
            for (User user:userList) {
                user.setBalance(ComputationUtil.randomMultipleOf100UpTo2000());
                user = userRepository.save(user);
                log.info("SAVED USER: {}",user);
                existingUsers.add(user);
            }
            addFriends(existingUsers);

        }
    }

    public void addFriends(List<User>  userList){
        for (int i=0; i<userList.size(); i++) {
            boolean isEven = i%2 == 0;
            int friendId = isEven? i+1 : i-1;
            List<User> friends = List.of(userList.get(friendId));
            User user = userList.get(i);
            user.setFriendsList(friends);
            userRepository.save(user);
        }
    }

    @Override
    public void run(String... args) {
        generateUser();
    }
}
