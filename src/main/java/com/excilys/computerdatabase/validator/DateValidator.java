package main.java.com.excilys.computerdatabase.validator;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DateValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateValidator.class);

    public static DateValidationResult assertValid(LocalDate introduced, LocalDate discontinued) {
        if (introduced == null || discontinued == null) {
            return new DateValidationResult(true, introduced, discontinued);
        } else if (introduced.isBefore(discontinued)) {
            return new DateValidationResult(true, introduced, discontinued);
        } else {
            LOGGER.error("Introduced date > discontinued date.");
            return new DateValidationResult(false, introduced, discontinued);
        }
    }

    public static DateValidationResult assertValid(String introducedString, String discontinuedString) {
        LocalDate introducedDate = null;
        LocalDate discontinuedDate = null;
        try {
            
        } catch (Exception e) {
            LOGGER.error("Introduced date > discontinued date.");
        }
        return assertValid(introducedDate, discontinuedDate);
    }

    
    
}

