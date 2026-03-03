package com.aparzero.maya.dto;

import com.aparzero.maya.enums.TransactionType;
import com.aparzero.maya.model.Transaction;
import com.aparzero.maya.util.TextUtil;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class TransactionDto {

    private int senderId;
    private String senderName;
    private int receiverId;
    private String receiverName;
    private BigDecimal amount;
    private int transactionId;
    private LocalDateTime transactionTimeStamp;
    private String transferMode;

    public static TransactionDto generate(Transaction transaction){

        return TransactionDto.builder()
                .transactionId(transaction.getTransactionId())
                .amount(transaction.getAmount())
                .senderName(TextUtil.maskName(transaction.getSenderName()))
                .receiverName(TextUtil.maskName(transaction.getReceiverName()))
                .senderId(transaction.getSenderId())
                .receiverId(transaction.getReceiverId())
                .transactionTimeStamp(transaction.getTransactionTimeStamp())
                .transferMode(transaction.getTransferMode())
                .build();
    }

    public static List<TransactionDto> batchConvert(List<Transaction> transactionList) {
        if ( transactionList == null) {
            return List.of();
        }
        return transactionList.stream()
                .map(TransactionDto::generate)
                .collect(Collectors.toList());
    }
}
