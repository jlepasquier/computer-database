package main.java.com.excilys.computerdatabase;

import main.java.com.excilys.computerdatabase.controller.CLIController;
import main.java.com.excilys.computerdatabase.ui.CLI;

/**
 * The Main Class.
 */
public class Main {

    /**
     * Entry point class.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        CLI cli = new CLI();
        CLIController controller = new CLIController(cli);

        controller.start();
    }
}
