package com.aparzero.maya.service;

import com.aparzero.maya.domain.ResponseObj;
import com.aparzero.maya.domain.TransactionRequest;
import com.aparzero.maya.dto.TransactionDto;
import com.aparzero.maya.model.Transaction;
import com.aparzero.maya.model.User;
import com.aparzero.maya.repo.TransactionRepository;
import com.aparzero.maya.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {


    private final UserRepository userRepository;
    private final UserService userService;
    private final TransactionRepository transactionRepository;

    @Transactional
    public ResponseEntity<ResponseObj> sendMoney(int senderId, TransactionRequest request){
        log.info("Transaction Request: {}",request);

        User sender = userService.validateUserId(senderId);
        User receiver = userService.validateUserId(request.getRecipientId());


        ResponseEntity<ResponseObj> response = null;

        if(userService.validateFriend(sender,receiver)){
            log.info("Recipient Is a friend");
            response = validateTransaction(sender,receiver,request);
        }
        else{
            log.info("No User Found Using The Ids: {} {}",senderId,request.getRecipientId());
            response = ResponseObj.failed("Invalid Recipient", HttpStatus.BAD_REQUEST.value());
        }
        return response;
    }


    public ResponseEntity<ResponseObj> getTransactionDetails(int userId, int transactionId){
        Optional<Transaction> optionalTransaction = transactionRepository.findByTransactionIdAndSenderIdOrTransactionIdAndReceiverId(transactionId,userId,transactionId,userId);
        ResponseEntity<ResponseObj> response;
        if(optionalTransaction.isEmpty()){
            response = ResponseObj.failed("User Has No Access To This Transction",HttpStatus.BAD_REQUEST.value());
        }
        else {
            response = ResponseObj.success(TransactionDto.generate(optionalTransaction.get()));
        }
        return response;

    }

    public ResponseEntity<ResponseObj> getUserTransactions(int userId, int size, int page){
        PageRequest pageable = PageRequest.of(
                page,
                size,
                Sort.by("transactionTimeStamp").descending()
        );
        Page<Transaction> transactionPage =
                transactionRepository
                        .findBySenderIdOrReceiverId(
                                userId,
                                userId,
                                pageable
                        );
        List<TransactionDto> transactionDtoList =
                TransactionDto.batchConvert(transactionPage.getContent());

        return ResponseObj.success(transactionDtoList);
    }

    private ResponseEntity<ResponseObj> validateTransaction(User sender,User receiver, TransactionRequest request){

        ResponseEntity<ResponseObj> response;

        BigDecimal currentLimit = Objects.isNull(sender.getTransactionLimit())?BigDecimal.ZERO:sender.getTransactionLimit();
        BigDecimal newCurrentLimit =  currentLimit.subtract(request.getAmount());

        log.info("NEW CURRENT LIMIT: {}",newCurrentLimit);

        if (newCurrentLimit.compareTo(BigDecimal.ZERO) < 0) {
           response = ResponseObj.failed("Transaction Limit Reached!!",HttpStatus.BAD_REQUEST.value());
        }
        else{
            sender.setTransactionLimit(newCurrentLimit);
            response = executeTransaction(sender,receiver,request);
        }
        return response;

    }

    private ResponseEntity<ResponseObj> executeTransaction(User sender,User receiver, TransactionRequest request){
        int debited = userRepository.debitUser(sender.getUserId(), request.getAmount());
        ResponseEntity<ResponseObj> response;
        if (debited == 0) {
            log.info("Insufficient Balance");
            response =  ResponseObj.failed("Insufficient Balance", HttpStatus.BAD_REQUEST.value());
        }
        else {
            int credited =  userRepository.creditUser(request.getRecipientId(), request.getAmount());
            if(credited == 0){
                log.info("Something went wrong!");
                response =  ResponseObj.failed("Something Went Wrong", HttpStatus.BAD_REQUEST.value());
            }
            else{
                Transaction transaction =Transaction.builder()
                        .transferMode("SEND MONEY")
                        .transactionTimeStamp(LocalDateTime.now())
                        .amount(request.getAmount())
                        .senderName(sender.getName())
                        .senderId(sender.getUserId())
                        .receiverName(receiver.getName())
                        .receiverId(receiver.getUserId())
                        .build();

                transaction = transactionRepository.save(transaction);
                response = ResponseObj.success(TransactionDto.generate(transaction));
                log.info("Transaction Success!!");
            }
        }

        return response;
    }


}
