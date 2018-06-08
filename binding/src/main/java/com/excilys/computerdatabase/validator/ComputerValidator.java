package com.excilys.computerdatabase.validator;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.model.CDBException;

public class ComputerValidator {

    public static void check(Computer cpu) throws CDBException {
        IdValidator.check(cpu.getId());
        NameValidator.check(cpu.getName());
        DateValidator.check(cpu.getIntroduced(), cpu.getDiscontinued());
    }
}
