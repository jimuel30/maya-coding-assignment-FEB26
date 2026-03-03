package com.aparzero.maya.scheduler;

import com.aparzero.maya.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
@RequiredArgsConstructor
public class DailySyncScheduler {

    private final UserRepository userRepository;



    @Value("${users.transaction-limit:1000}")
    private BigDecimal transactionLimit;



    @Scheduled(cron = "${scheduler.daily-sync.cron}")
    public void runDailySync() {
        log.info("Running Scheduler");
        updateTransactionLimit();
    }

    private void updateTransactionLimit(){
        log.info("UPDATING ALL USERS TRANSACITON LIMIT");
        int updatedAccountCount = userRepository.updateTransactionLimitForAllUsers(transactionLimit);
        log.info("UPDATE ACCOUNTS: {}",updatedAccountCount);
    }
}
