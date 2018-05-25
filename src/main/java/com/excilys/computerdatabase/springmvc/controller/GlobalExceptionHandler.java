package main.java.com.excilys.computerdatabase.springmvc.controller;

import java.nio.file.AccessDeniedException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handle(AccessDeniedException exception) {
        ModelAndView model = new ModelAndView("403");
        return model;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handle(NoHandlerFoundException exception) {
        ModelAndView model = new ModelAndView("404");
        return model;
    }

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handle(RuntimeException exception) {
        ModelAndView model = new ModelAndView("500");
        return model;
    }
}
