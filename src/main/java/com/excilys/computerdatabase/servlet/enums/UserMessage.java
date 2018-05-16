package main.java.com.excilys.computerdatabase.servlet.enums;

public enum UserMessage {

    UPDATE_SUCCESS("The computer has been succesfully updated."),
    CREATION_SUCCESS("The computer has been succesfully added."),
    DELETION_SUCCESS("The computers have been succesfully removed."),
    DELETION_FAIL("The computer deletion failed.");

    private final String message;

    UserMessage(final String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
