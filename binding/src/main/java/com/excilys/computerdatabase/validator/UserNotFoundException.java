package com.excilys.computerdatabase.validator;

import com.excilys.computerdatabase.model.CDBException;

public class UserNotFoundException extends CDBException {
    private static final long serialVersionUID = 1L;

    public UserNotFoundException() {
        super("Error : can't find this user.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
