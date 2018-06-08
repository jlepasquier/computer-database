package com.excilys.computerdatabase.model;

public class CDBException extends Exception {

    private static final long serialVersionUID = 1L;

    public CDBException() {
        super("A CDB error occured");
    }

    public CDBException(String message) {
        super(message);
    }
}
