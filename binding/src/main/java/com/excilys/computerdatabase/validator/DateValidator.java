package com.excilys.computerdatabase.validator;

import java.time.LocalDate;

import com.excilys.computerdatabase.model.CDBException;

public class DateValidator {

    public static void check(LocalDate introduced, LocalDate discontinued) throws CDBException {
        if (introduced != null && discontinued != null) {
            if (introduced.isAfter(discontinued)) {
                throw new IntroducedAferDiscontinuedException();
            }
        }
    }
}