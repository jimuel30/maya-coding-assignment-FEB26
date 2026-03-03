package com.aparzero.maya.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    private int recipientId;
    private BigDecimal amount;
}
