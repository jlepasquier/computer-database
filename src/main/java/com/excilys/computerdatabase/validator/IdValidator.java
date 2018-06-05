package main.java.com.excilys.computerdatabase.validator;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import main.java.com.excilys.computerdatabase.exception.CDBException;
import main.java.com.excilys.computerdatabase.exception.InvalidIdException;

public class IdValidator {
    public static Set<Long> checkList(String ids) throws CDBException {
        if (StringUtils.isBlank(ids)) {
            throw new InvalidIdException();
        }
        try {
            return Arrays.stream(ids.split(",")).map(id -> Long.parseLong(id)).collect(Collectors.toSet());
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidIdException();
        }
    }

    public static void check(long id) throws CDBException {
        if (id < 0) {
            throw new InvalidIdException();
        }
    }
}
