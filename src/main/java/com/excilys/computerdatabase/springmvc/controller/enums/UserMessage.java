package main.java.com.excilys.computerdatabase.springmvc.controller.enums;

public enum UserMessage {

    UPDATE_SUCCESS("The computer has been succesfully updated."),
    UPDATE_FAIL("The computer update has failed."),
    CREATION_SUCCESS("The computer has been succesfully added."),
    CREATION_FAIL("The computer creation has failed."),
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
