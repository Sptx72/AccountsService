package com.microcompany.accountsservice.exception;

public class UserNotFoundException  extends GlobalException {
    protected static final long serialVersionUID = 3L;

    public UserNotFoundException() {
        super("Account not found");
    }

    public UserNotFoundException(Long ownerId) {
        super("Account with id: " + ownerId + " not found");
    }

}
