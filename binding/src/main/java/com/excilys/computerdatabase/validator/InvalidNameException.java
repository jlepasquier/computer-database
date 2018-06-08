package com.excilys.computerdatabase.validator;

import com.excilys.computerdatabase.model.CDBException;

public class InvalidNameException extends CDBException {
    private static final long serialVersionUID = 1L;

    public InvalidNameException() {
        super("Error : name is not valid.");
    }

    public InvalidNameException(String message) {
        super(message);
    }
}
