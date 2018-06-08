package com.excilys.computerdatabase.validator;

import com.excilys.computerdatabase.model.CDBException;

public class IntroducedAferDiscontinuedException extends CDBException {
    private static final long serialVersionUID = 1L;

    public IntroducedAferDiscontinuedException() {
        super("Error : introduction date is after discontinuation date.");
    }

    public IntroducedAferDiscontinuedException(String message) {
        super(message);
    }
}
