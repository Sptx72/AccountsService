package com.microcompany.accountsservice.controller;

import com.microcompany.accountsservice.AccountsServiceApplication;
import com.microcompany.accountsservice.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = AccountsServiceApplication.class)
@ActiveProfiles("dev")
public class AccountsServiceTestRestTemplate {


    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    public void givenUrl_whenDepositMoney_thenStatusOkAndBalanceIsGreaterOrEqualToAmount() throws Exception {
        int amount=100;
        String URIPut ="/accounts/deposit?accountId=1&amount=" + amount + "&ownerId=1";
         Account account =new Account(1L, "algo",new Date(),0,1L,null);
         RequestEntity<Void> request = RequestEntity.put(URIPut).contentType(MediaType.APPLICATION_JSON).build();
       ResponseEntity<Account> response = restTemplate.exchange(
               request, Account.class
       );
        System.out.println(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getBalance()).isGreaterThanOrEqualTo(amount);
        assertThat(response.getHeaders().getContentType()).isEqualTo(MediaType.APPLICATION_JSON);
    }
    @Test
     public void givenUrl_whenDepositNegativeInteger_thenStatusPreconditionFailedAndBalanceIsGreaterOrEqualToAmount() throws Exception {
        int amount=-100;
        String URIPut ="/accounts/deposit?accountId=1&amount=" + amount + "&ownerId=1";
         Account account =new Account(1L, "algo",new Date(),0,1L,null);
         RequestEntity<Account> request = RequestEntity.put(URIPut).contentType(MediaType.APPLICATION_JSON).body(null);
       ResponseEntity<Account> response = restTemplate.exchange(
               request, Account.class
       );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED);
    }
    @Test
    public void givenUser_whenDeleteAccountsByValidUserId_thenStatus204() throws Exception {
        String URIDelete="/accounts/byUser/1";
        HttpHeaders headers = new HttpHeaders();
        headers.add("ACCEPT", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Account> request = new HttpEntity<>(null,headers);
        ResponseEntity<Account> response = restTemplate.exchange(
               URIDelete,HttpMethod.DELETE,request,Account.class
        );
        System.out.println(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

       @Test
    public void givenUser_whenDeleteAccountsByNotValidUserId_thenStatus404() throws Exception {
        String URIDelete="/accounts/byUser/100";
        HttpHeaders headers = new HttpHeaders();
        headers.add("ACCEPT", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Account> request = new HttpEntity<>(null,headers);
        ResponseEntity<Account> response = restTemplate.exchange(
               URIDelete,HttpMethod.DELETE,request,Account.class
        );
        System.out.println(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }
}
