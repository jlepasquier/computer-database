package main.java.com.excilys.computerdatabase.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import main.java.com.excilys.computerdatabase.service.CompanyService;
import main.java.com.excilys.computerdatabase.service.ComputerService;

@Controller
@RequestMapping("/computer")
public class ComputerController {

    private ComputerService computerService;
    private CompanyService companyService;

    public ComputerController(ComputerService computerService, CompanyService companyService) {
        super();
        this.computerService = computerService;
        this.companyService = companyService;
    }

    @RequestMapping(value = { "/dashboard", "/" }, method = RequestMethod.GET)
    public String dashboard(ModelMap model) {
        model.addAttribute("computerCount", 5);
        return "dashboard";
    }

}