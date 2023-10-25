package com.ikem.dotniptest.payload;

import com.ikem.dotniptest.annotation.AccountNumber;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DepositDto {

    @Min(value = 100)
    private BigDecimal amount;

    @AccountNumber
    private String accountNumber;

    private BigDecimal currentBalance;

    private String message;
}
