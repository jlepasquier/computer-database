package com.excilys.computerdatabase.validator;

import com.excilys.computerdatabase.model.CDBException;

public class InvalidIdException extends CDBException {
    private static final long serialVersionUID = 1L;

    public InvalidIdException() {
        super("Error : id is not valid.");
    }

    public InvalidIdException(String message) {
        super(message);
    }
}
