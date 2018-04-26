package main.java.com.excilys.computerdatabase.exception;

/**
 * The Class InvalidComputerIdException.
 */
public class InvalidComputerIdException extends Exception {

    /**
     * Instantiates a new missing computer id exception.
     */
    public InvalidComputerIdException() {
        super("Error : this computer has no ID.");
    }

    /**
     * Instantiates a new missing computer id exception.
     * @param message the message
     */
    public InvalidComputerIdException(String message) {
        super(message);
    }
}
