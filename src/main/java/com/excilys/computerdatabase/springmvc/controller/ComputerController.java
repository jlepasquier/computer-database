package main.java.com.excilys.computerdatabase.springmvc.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import main.java.com.excilys.computerdatabase.dto.ComputerDTO;
import main.java.com.excilys.computerdatabase.exception.CDBException;
import main.java.com.excilys.computerdatabase.mapper.ComputerDTOMapper;
import main.java.com.excilys.computerdatabase.model.Computer;
import main.java.com.excilys.computerdatabase.service.CompanyService;
import main.java.com.excilys.computerdatabase.service.ComputerService;

@Controller
@RequestMapping("/")
public class ComputerController {

    private ComputerService computerService;
    private CompanyService companyService;

    private static final String DEFAULT_PAGE = "1";
    private static final String DEFAULT_SEARCH = "";

    public ComputerController(ComputerService computerService, CompanyService companyService) {
        super();
        this.computerService = computerService;
        this.companyService = companyService;
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView getDashboard(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(value = "search", defaultValue = DEFAULT_SEARCH) String search) {

        ModelAndView modelAndView = new ModelAndView("dashboard");

        List<Computer> cpuList = computerService.getComputerList(page);
        List<ComputerDTO> dtoList = ComputerDTOMapper.createDTOList(cpuList);

        Long totalPages, computerCount;
        try {
            totalPages = computerService.getComputerPageCount();
            computerCount = computerService.getComputerCount();
        } catch (CDBException e) {
            totalPages = 0L;
            computerCount = 0L;
        }

        modelAndView.addObject("page", page);
        modelAndView.addObject("dtoList", dtoList);
        modelAndView.addObject("totalPages", totalPages);
        modelAndView.addObject("computerCount", computerCount);

        return modelAndView;
    }

}