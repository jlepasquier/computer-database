package main.java.com.excilys.computerdatabase.validator;

import main.java.com.excilys.computerdatabase.exception.CDBException;
import main.java.com.excilys.computerdatabase.exception.InvalidNameException;

public class NameValidator {
    public static void check(String name) throws CDBException {
        if (name == null || name.length() < 3) {
            throw new InvalidNameException();
        }
    }
}
