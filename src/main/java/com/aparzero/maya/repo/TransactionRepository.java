package com.aparzero.maya.repo;

import com.aparzero.maya.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Optional<Transaction> findByTransactionIdAndSenderIdOrTransactionIdAndReceiverId(
            int transactionId1, int senderId,
            int transactionId2, int receiverId
    );

    Page<Transaction> findBySenderIdOrReceiverId(
            int senderId,
            int receiverId,
            Pageable pageable
    );
}
