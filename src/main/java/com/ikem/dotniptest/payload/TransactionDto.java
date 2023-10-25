package com.ikem.dotniptest.payload;

import com.ikem.dotniptest.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    public UUID transactionReference;
    public BigDecimal amount;
    public BigDecimal transactionFee;
    public BigDecimal billedAmount;
    public String description;

    public LocalDateTime dateCreated;
    public Status status;
    public boolean commissionWorthy;
    public BigDecimal commission;
    public String sourceAccountNumber;
    public String destinationAccountNumber;
}
