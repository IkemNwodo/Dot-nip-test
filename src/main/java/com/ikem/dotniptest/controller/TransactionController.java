package com.ikem.dotniptest.controller;

import com.ikem.dotniptest.payload.DepositDto;
import com.ikem.dotniptest.payload.Response;
import com.ikem.dotniptest.payload.TransferDto;
import com.ikem.dotniptest.service.TransactionService;
import com.ikem.dotniptest.service.TransactionSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionSummaryService transactionSummaryService;

    @PostMapping("/transfer")
    public ResponseEntity<String> processTransfer(@RequestBody TransferDto transfer){
        transactionService.processTransaction(transfer);
        return ResponseEntity.ok("Transfer Successful");
    }

    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.CREATED)
    public DepositDto fundAccount(@Validated @RequestBody DepositDto depositDto){
        return transactionService.deposit(depositDto);
    }
    @GetMapping
    public ResponseEntity<Response> getTransactions(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam (value = "accountNumber", required = false) String accountNumber,
            @RequestParam (value = "startDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate startDate,
            @RequestParam (value ="endDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate endDate){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("Transactions ", transactionService.getAllTransactions(status, accountNumber, startDate, endDate)))
                        .message(OK.getReasonPhrase() + ": Get all transactions")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/summary")
    public ResponseEntity<Response> getTransactionSummary(@RequestParam ("date") @DateTimeFormat(pattern="yyyy-MM-dd")  LocalDate date){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("Transactions Summary ", transactionSummaryService.createSummary(date)))
                        .message(OK.getReasonPhrase() + ": Summary of all Transactions for " + date)
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
}
