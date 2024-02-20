package com.microcompany.accountsservice.config;

import com.microcompany.accountsservice.exception.AccountNotfoundException;
import com.microcompany.accountsservice.exception.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {
 @ExceptionHandler(GlobalException.class)
 ResponseEntity<Object> noSuchElementExceptionHandler(GlobalException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
     @ExceptionHandler(AccountNotfoundException.class)
    ResponseEntity<Object> noSuchElementExceptionHandler(AccountNotfoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Excepcion de cuenta");
    }
}
