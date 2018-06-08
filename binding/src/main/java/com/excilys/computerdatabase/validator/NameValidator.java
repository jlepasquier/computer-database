package com.excilys.computerdatabase.validator;

import com.excilys.computerdatabase.model.CDBException;

public class NameValidator {
    public static void check(String name) throws CDBException {
        if (name == null || name.length() < 3) {
            throw new InvalidNameException();
        }
    }
}
