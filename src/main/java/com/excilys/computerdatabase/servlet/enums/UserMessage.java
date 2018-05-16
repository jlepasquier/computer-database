package main.java.com.excilys.computerdatabase.servlet.enums;

public enum UserMessage {

    SUCCESFUL_UPDATE("The computer has been succesfully updated."),
    SUCCESFUL_CREATION("The computer has been succesfully added.");
    

    private final String message;

    UserMessage(final String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
