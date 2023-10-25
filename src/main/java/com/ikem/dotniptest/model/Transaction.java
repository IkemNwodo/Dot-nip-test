package com.ikem.dotniptest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    public UUID transactionReference;
    public BigDecimal amount;
    public BigDecimal transactionFee;
    public BigDecimal billedAmount;
    public String description;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", updatable = false, nullable = false)
    public LocalDateTime dateCreated;
    @Enumerated(EnumType.STRING)
    public Status status;
    public boolean commissionWorthy;
    public BigDecimal commission;
    public String sourceAccountNumber;
    public String destinationAccountNumber;
    @ManyToOne
    @JoinColumn(name = "account_number")
    private Account account;
}
