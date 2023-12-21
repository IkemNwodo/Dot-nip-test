package com.ikem.dotniptest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikem.dotniptest.payload.CreateAccountDto;
import com.ikem.dotniptest.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;
    @Test
    void create_account() throws Exception {

        var account1 = CreateAccountDto.builder()
                .firstName("Ikemefuna")
                .lastName("Nwodo")
                .BVN("12345678901")
                .password("1234567890")
                .emailAddress("nwodofrank@gmail.com")
                .build();

        mvc.perform(post("/api/v1/account/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(account1)))
                .andExpect(status().isCreated());
    }
}
