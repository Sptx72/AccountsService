package com.microcompany.accountsservice.controller;

import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.services.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Tag(name = "Accounts API", description = "Accounts management APIs")
@RestController
@RequestMapping("/accounts")
@Validated
public class AccountController {

    @Autowired
    IAccountService accountService;

    @Operation(summary = "Get Accounts", description = "Get all Accounts")
    @GetMapping
    public ResponseEntity<List<Account>> getAll() {
        return ResponseEntity.ok(accountService.getAccounts());
    }

    @Operation(summary = "Get Account", description = "Get one Account by id")
    @GetMapping("/{id}")
    public ResponseEntity<Account> getOne(
            @Parameter(name = "id", description = "Account ID")
            @NonNull @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }

    @Operation(summary = "Create Account", description = "Create Account for an User")
    @PostMapping
    public ResponseEntity<Account> create(
            @Parameter(name = "account", description = "Account Entity")
            @Valid @RequestBody Account account
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.create(account));
    }

    @Operation(summary = "Update Account")
    @PutMapping("/{id}")
    public ResponseEntity<Account> update(
            @Parameter(name = "id", description = "Account ID")
            @NonNull @PathVariable("id") Long id,
            @Parameter(name = "id", description = "Account data Entity")
            @Valid @RequestBody Account account
    ) {
        return ResponseEntity.ok(accountService.updateAccount(id, account));
    }

    @Operation(summary = "Delete Account", description = "Delete Account by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @Parameter(name = "id", description = "Account ID")
            @NonNull @PathVariable("id") Long id
    ) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deposit", description = "Add money to an account")
    @PutMapping("/deposit")
    public ResponseEntity<Account> deposit(
            @Parameter(name = "accountId", description = "Account ID")
            @RequestParam("accountId") Long accountId,
            @Parameter(name = "amount", description = "Amount to deposit")
            @RequestParam("amount") @Min(1) Integer amount,
            @Parameter(name = "ownerId", description = "User ID")
            @RequestParam("ownerId") Long ownerId) {
        return ResponseEntity.ok(accountService.addBalance(accountId, amount, ownerId));
    }

    @Operation(summary = "Withdraw", description = "Subtract money of an account")
    @PutMapping("/withdraw")
    public ResponseEntity<Account> withdraw(
            @Parameter(name = "accountId", description = "Account ID")
            @RequestParam("accountId") Long accountId,
            @Parameter(name = "amount", description = "Amount to subtract")
            @RequestParam("amount") @Min(1) Integer amount,
            @Parameter(name = "ownerId", description = "User ID")
            @RequestParam("ownerId") Long ownerId) {
        return ResponseEntity.ok(accountService.withdrawBalance(accountId, amount, ownerId));
    }

    @Operation(summary = "Delete User Accounts", description = "Delete all the accounts for an User")
    @DeleteMapping("/byUser/{id}")
    public ResponseEntity<?> deleteAllByUser(
            @Parameter(name = "id", description = "User ID")
            @NonNull @PathVariable("id") Long ownerId
    ) {
        accountService.deleteAccountsUsingOwnerId(ownerId);
        return ResponseEntity.noContent().build();
    }

}
