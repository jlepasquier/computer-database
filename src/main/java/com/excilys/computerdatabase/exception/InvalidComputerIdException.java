package main.java.com.excilys.computerdatabase.exception;

/**
 * The Class InvalidComputerIdException.
 */
public class InvalidComputerIdException extends CDBException {
    
    private static final long serialVersionUID = 1L;

    public InvalidComputerIdException() {
        super("Error : this computer has no ID.");
    }

    public InvalidComputerIdException(String message) {
        super(message);
    }
}
