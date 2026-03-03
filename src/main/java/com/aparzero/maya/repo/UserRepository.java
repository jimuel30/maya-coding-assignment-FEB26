package com.aparzero.maya.repo;

import com.aparzero.maya.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Query("UPDATE User u SET u.balance = u.balance - :amount WHERE u.userId = :userId AND u.balance >= :amount")
    int debitUser(@Param("userId") int userId, @Param("amount") BigDecimal amount);

    // Credit user (add amount)
    @Modifying
    @Query("UPDATE User u SET u.balance = u.balance + :amount WHERE u.userId = :userId")
    int creditUser(@Param("userId") int userId, @Param("amount") BigDecimal amount);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.transactionLimit = :limit")
    int updateTransactionLimitForAllUsers(@Param("limit") BigDecimal limit);

}
