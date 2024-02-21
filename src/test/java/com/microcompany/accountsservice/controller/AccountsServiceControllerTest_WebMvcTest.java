package com.microcompany.accountsservice.controller;

import com.microcompany.accountsservice.exception.AccountNotfoundException;
import com.microcompany.accountsservice.exception.UserNotFoundException;
import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.persistence.AccountRepository;
import com.microcompany.accountsservice.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
public class AccountsServiceControllerTest_WebMvcTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService service;

    @MockBean
    private AccountRepository repository;

    public AccountsServiceControllerTest_WebMvcTest() {
    }
    private final Long fakeAccountId= 100L;
    private final  int amount = 100;
    private final Long realUser=1l;
    private final Long fakeUser=17294L;

    @BeforeEach
    public void setUp() {

        List<Account> accounts = Arrays.asList(
                new Account(realUser, "ejemplo", new Date(),10293,realUser,null)
        );

        Mockito.when(service.addBalance(eq(fakeAccountId),Mockito.anyInt(),Mockito.any(Long.class)))
                .thenThrow(
                    new AccountNotfoundException(fakeAccountId)
                );

        Mockito.when(service.getAccount(realUser))
                .thenReturn(accounts.stream().filter(account -> account.getId().equals(realUser)).findAny().get());

        Mockito.when(service.addBalance(1L,amount,realUser))
                .thenReturn(accounts.stream().filter(account -> account.getId().equals(1L)).findAny().get());
        Mockito.when(service.deleteAccountsUsingOwnerId(fakeUser)).thenThrow(new UserNotFoundException(fakeUser));

        Mockito.when(repository.findAll())
                .thenReturn(accounts);

        Mockito.when(repository.save(Mockito.any(Account.class)))
                .thenAnswer(elem -> {
                    Account ap = (Account) elem.getArguments()[0];
                    ap.setId(100L);
                    return ap;
                });

    }
    @Test
    public void givenAccounts_whenDepositMoney_thenStatus200() throws Exception {

        MvcResult result = mvc.perform(put("/accounts/deposit?accountId=1&amount=" + amount + "&ownerId=1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance", is(greaterThanOrEqualTo(amount)))).andReturn();


    }
    @Test
    public void givenWrongAccount_whenDepositMoney_thenStatus404() throws Exception {
        int amount = 100;
        mvc.perform(put("/accounts/deposit?accountId="+fakeAccountId+"&amount=" + amount + "&ownerId=1")
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
        mvc.perform(delete("/accounts/byUser/"+fakeUser)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }
}
