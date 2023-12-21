package com.ikem.dotniptest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikem.dotniptest.payload.CreateAccountDto;
import com.ikem.dotniptest.payload.DepositDto;
import com.ikem.dotniptest.payload.TransferDto;
import com.ikem.dotniptest.service.TransactionService;
import com.ikem.dotniptest.service.TransactionSummaryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    TransactionSummaryService transactionSummaryService;

    @Test
    void test_deposit() throws Exception {
        var depositDto = DepositDto.builder()
                .accountNumber("0000000001")
                .amount(BigDecimal.valueOf(100000.00))
                .message("Self deposit")
                .build();

        mvc.perform(post("/api/v1/account/transaction/deposit")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(depositDto)))
                .andExpect(status().isCreated());
    }
    @Test
    void test_process_transfer() throws Exception {

        var transfer = TransferDto.builder()
                .amount(BigDecimal.valueOf(10000.00))
                .sourceAccountNumber("0000000001")
                .destinationAccountNumber("0000000002")
                .build();

        mvc.perform(post("/api/v1/account/transaction/transfer")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(transfer)))
                .andExpect(status().isOk());
    }

    @Test
    void get_transactions_using_status() throws Exception {

        mvc.perform(get("/api/v1/account/transaction")
                        .contentType("application/json")
                        .param("status","Transaction_Successful"))
                .andExpect(status().isOk());
    }

    @Test
    void get_transactions_using_account_number() throws Exception {

        mvc.perform(get("/api/v1/account/transaction")
                        .contentType("application/json")
                        .param("accountNumber","0000000002"))
                .andExpect(status().isOk());
    }

    @Test
    void get_transactions_using_start_date() throws Exception {

        mvc.perform(get("/api/v1/account/transaction")
                        .contentType("application/json")
                        .param("startDate","2023-12-20"))
                .andExpect(status().isOk());
    }

    @Test
    void get_transactions_using_end_date() throws Exception {

        mvc.perform(get("/api/v1/account/transaction")
                        .contentType("application/json")
                        .param("endDate","2023-12-20"))
                .andExpect(status().isOk());
    }

    @Test
    void get_transactions_using_all_param() throws Exception {

        mvc.perform(get("/api/v1/account/transaction")
                        .contentType("application/json")
                        .param("status","Transaction_Successful")
                        .param("accountNumber","0000000002")
                        .param("startDate","2023-12-20")
                        .param("endDate","2023-12-20"))
                .andExpect(status().isOk());
    }

    @Test
    void get_transaction_summary() throws Exception {

        mvc.perform(get("/api/v1/account/transaction/summary")
                        .contentType("application/json")
                        .param("date","2023-12-20"))
                .andExpect(status().isOk());
    }
}
