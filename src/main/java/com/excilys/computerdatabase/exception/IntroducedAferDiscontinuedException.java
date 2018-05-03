package main.java.com.excilys.computerdatabase.exception;

public class IntroducedAferDiscontinuedException extends CDBException {
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new missing computer id exception.
     */
    public IntroducedAferDiscontinuedException() {
        super("Error : introduction date is after discontinuation date.");
    }

    /**
     * Instantiates a new missing computer id exception.
     * @param message the message
     */
    public IntroducedAferDiscontinuedException(String message) {
        super(message);
    }
}
