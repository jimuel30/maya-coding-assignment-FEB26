package com.aparzero.maya.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String email;
    private BigDecimal balance;
    private String name;
    private String phone;
    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<User> friendsList;

}
