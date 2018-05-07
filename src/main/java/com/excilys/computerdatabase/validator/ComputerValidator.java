package main.java.com.excilys.computerdatabase.validator;

import main.java.com.excilys.computerdatabase.exception.CDBException;
import main.java.com.excilys.computerdatabase.model.Computer;

public class ComputerValidator {

    public static void check(Computer cpu) throws CDBException {
        IdValidator.check(cpu.getId());
        NameValidator.check(cpu.getName());
        DateValidator.check(cpu.getIntroduced(), cpu.getDiscontinued());
    }
}
