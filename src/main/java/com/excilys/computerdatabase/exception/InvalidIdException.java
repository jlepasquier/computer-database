package main.java.com.excilys.computerdatabase.exception;

public class InvalidIdException extends CDBException {
    private static final long serialVersionUID = 1L;

    public InvalidIdException() {
        super("Error : id is not valid.");
    }

    public InvalidIdException(String message) {
        super(message);
    }
}
