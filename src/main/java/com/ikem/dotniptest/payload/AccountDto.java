package com.ikem.dotniptest.payload;

import com.ikem.dotniptest.model.Transaction;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
public class AccountDto {

    private String accountNumber;
    private String accountName;
    private BigDecimal balance;
    private Set<Transaction> transactions;

}
