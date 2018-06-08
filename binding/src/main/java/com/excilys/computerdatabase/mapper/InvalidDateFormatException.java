package com.excilys.computerdatabase.mapper;

import com.excilys.computerdatabase.model.CDBException;

/**
 * The Class InvalidDateFormatException.
 */
public class InvalidDateFormatException extends CDBException {
    private static final long serialVersionUID = 1L;

    public InvalidDateFormatException() {
        super("Error : wrong date format.");
    }

    public InvalidDateFormatException(String message) {
        super(message);
    }
}
