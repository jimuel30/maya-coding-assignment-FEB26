package com.aparzero.maya.dto;

import com.aparzero.maya.model.User;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.*;

@Data
@Builder
public class UserDto {
    private int id;
    private String name;
    private BigDecimal balance;
    private BigDecimal transactionLimit;


    public static UserDto convert(User user){

        List<FriendDto> friendDtoList = FriendDto.batchConvert(user.getFriendsList());

        return UserDto.builder()
                .id(user.getUserId())
                .name(user.getName())
                .balance(user.getBalance())
                .transactionLimit(user.getTransactionLimit())
                .build();
    }
}
