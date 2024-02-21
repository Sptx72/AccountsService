package com.microcompany.accountsservice.config;

import com.microcompany.accountsservice.exception.AccountNotfoundException;
import com.microcompany.accountsservice.exception.GlobalException;
import com.microcompany.accountsservice.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

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
     @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<Object> noSuchElementExceptionHandler(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
     @ExceptionHandler(ConstraintViolationException.class)
     ResponseEntity<Object> constraintViolationExceptionHandler(ConstraintViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
