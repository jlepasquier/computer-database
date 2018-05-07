package main.java.com.excilys.computerdatabase.validator;

import java.time.LocalDate;

import main.java.com.excilys.computerdatabase.exception.CDBException;
import main.java.com.excilys.computerdatabase.exception.IntroducedAferDiscontinuedException;

public class DateValidator {

    public static void check(LocalDate introduced, LocalDate discontinued) throws CDBException {
        if (introduced != null && discontinued != null) {
            if (introduced.isAfter(discontinued)) {
                throw new IntroducedAferDiscontinuedException();
            }
        }
    }
}