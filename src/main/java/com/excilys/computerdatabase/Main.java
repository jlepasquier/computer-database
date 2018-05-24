package main.java.com.excilys.computerdatabase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import main.java.com.excilys.computerdatabase.controller.CLIController;
import main.java.com.excilys.computerdatabase.springmvc.config.WebAppConfig;

/**
 * The Main Class.
 */
public class Main {

    /**
     * Entry point class.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        ApplicationContext context = new AnnotationConfigApplicationContext(WebAppConfig.class);

        CLIController controller = context.getBean(CLIController.class);
        controller.start();
    }
}
