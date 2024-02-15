package com.microcompany.accountsservice.controller;

import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.services.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    IAccountService accountService;

    @GetMapping
    public ResponseEntity<List<Account>> getAll() {
        return ResponseEntity.ok(accountService.getAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getOne(@NonNull @PathVariable("id") Long id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }

    @PostMapping
    public ResponseEntity<Account> create(@Valid @RequestBody Account account) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.create(account));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> update(@NonNull @PathVariable("id") Long id, @Valid @RequestBody Account account) {
        return ResponseEntity.ok(accountService.updateAccount(id, account));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@NonNull @PathVariable("id") Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //id, cantidad, idusuario     withdraw
    @PutMapping("/deposit")
    public ResponseEntity<Account> deposit(
            @RequestParam("accountId") Long accountId,
            @RequestParam("amount") @Min(1) Integer amount,
            @RequestParam("ownerId") Long ownerId) {
        return ResponseEntity.ok(accountService.addBalance(accountId, amount, ownerId));
    }

    @PutMapping("/withdraw")
    public ResponseEntity<Account> withdraw(
            @RequestParam("accountId") Long accountId,
            @RequestParam("amount") @Min(1) Integer amount,
            @RequestParam("ownerId") Long ownerId) {
        return ResponseEntity.ok(accountService.withdrawBalance(accountId, amount, ownerId));
    }

    @DeleteMapping("/byUser/{id}")
    public ResponseEntity<?> deleteAllByUser(@NonNull @PathVariable("id") Long ownerId) {
        accountService.deleteAccountsUsingOwnerId(ownerId);
        return ResponseEntity.noContent().build();
    }

}
