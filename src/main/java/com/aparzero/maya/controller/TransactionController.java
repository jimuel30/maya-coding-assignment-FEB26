package com.aparzero.maya.controller;


import com.aparzero.maya.domain.ResponseObj;
import com.aparzero.maya.domain.TransactionRequest;
import com.aparzero.maya.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
@Slf4j
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/send-money")
    ResponseEntity<ResponseObj> sendMoney(@RequestHeader("X-User-Id") int userId, @RequestBody TransactionRequest request){
        log.info("Sending Money For: {}",userId);
        return transactionService.sendMoney(userId,request);
    }

    @GetMapping("/{transactionId}")
    ResponseEntity<ResponseObj> getTransactionDetails(@RequestHeader("X-User-Id") int userId, @PathVariable int transactionId){
        log.info("Getting Transaction Id: {} For: {}",transactionId, userId );
        return transactionService.getTransactionDetails(userId,transactionId);
    }
    @GetMapping("")
    ResponseEntity<ResponseObj> getUserTransactions(@RequestHeader("X-User-Id") int userId,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size){
        log.info("Getting Transactions For: {}", userId );
        return transactionService.getUserTransactions(userId,size,page);
    }


}