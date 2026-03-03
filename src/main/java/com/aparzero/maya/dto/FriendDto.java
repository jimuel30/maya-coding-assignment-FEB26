package com.aparzero.maya.dto;

import com.aparzero.maya.model.User;
import lombok.Builder;
import lombok.Data;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
public class FriendDto {
    private int id;
    private String name;


    public static FriendDto convert(User friend){
        return FriendDto.builder()
                .id(friend.getUserId())
                .name(friend.getName())
                .build();
    }


    public static List<FriendDto> batchConvert(List<User> friendList) {
        if (friendList == null) {
            return List.of();
        }
        return friendList.stream()
                .map(FriendDto::convert)
                .collect(Collectors.toList());
    }
}
