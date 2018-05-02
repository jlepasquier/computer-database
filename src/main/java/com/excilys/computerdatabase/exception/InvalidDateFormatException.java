package main.java.com.excilys.computerdatabase.exception;

/**
 * The Class InvalidDateFormatException.
 */
public class InvalidDateFormatException extends Exception {

    /**
     * Instantiates a new missing computer id exception.
     */
    public InvalidDateFormatException() {
        super("Error : wrong date format.");
    }

    /**
     * Instantiates a new missing computer id exception.
     * @param message the message
     */
    public InvalidDateFormatException(String message) {
        super(message);
    }
}
