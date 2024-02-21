package com.microcompany.accountsservice.controller;

import com.microcompany.accountsservice.AccountsServiceApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = AccountsServiceApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class AccountControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    public void givenAccounts_whenDepositMoney_thenStatus200() throws Exception {
        int amount = 100;
        mvc.perform(put("/accounts/deposit?accountId=1&amount=" + amount + "&ownerId=1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance", is(greaterThanOrEqualTo(amount))));
    }

    @Test
    public void givenAccounts_whenDepositMoney_thenStatus404() throws Exception {
        int amount = 100;
        mvc.perform(put("/accounts/deposit?accountId=100&amount=" + amount + "&ownerId=1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenUser_whenDeleteAccounts_thenStatus204() throws Exception {
        mvc.perform(delete("/accounts/byUser/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenUser_whenDeleteAccounts_thenStatus404() throws Exception {
        mvc.perform(delete("/accounts/byUser/72")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }
    @AfterEach
    public void reloadDB(){

    }
}
