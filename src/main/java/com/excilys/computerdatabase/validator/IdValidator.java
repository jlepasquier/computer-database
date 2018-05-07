package main.java.com.excilys.computerdatabase.validator;

import main.java.com.excilys.computerdatabase.exception.CDBException;
import main.java.com.excilys.computerdatabase.exception.InvalidIdException;

public class IdValidator {
    public static void check(Long id) throws CDBException {
        if (id<0) {
            throw new InvalidIdException();
        }
    }
}
