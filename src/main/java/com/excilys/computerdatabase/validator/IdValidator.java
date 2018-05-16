package main.java.com.excilys.computerdatabase.validator;

import java.util.Arrays;
import java.util.stream.Collectors;

import main.java.com.excilys.computerdatabase.exception.CDBException;
import main.java.com.excilys.computerdatabase.exception.InvalidIdException;

public class IdValidator {
    public static String checkList(String ids) throws CDBException {

        try {
            return Arrays.stream(ids.split(",")).map(id -> Long.parseLong(id)).collect(Collectors.toSet()).toString()
                    .replace("[", "(").replace("]", ")");
        } catch (Exception e) {
            throw new InvalidIdException();
        }

    }
    

    public static void check(long id) throws CDBException {
        if (id<0) {
            throw new InvalidIdException();
        }
    }
}
