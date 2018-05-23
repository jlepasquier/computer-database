package main.java.com.excilys.computerdatabase.springmvc.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

        List<Computer> cpuList;
        Long totalPages;
        Long computerCount;
        try {
            if (StringUtils.isBlank(search)) {
                cpuList = computerService.getComputerList(page);
                totalPages = computerService.getComputerPageCount();
                computerCount = computerService.getComputerCount();
            } else {
                cpuList = computerService.searchComputer(search, page);
                totalPages = computerService.getSearchComputerPageCount(search);
                computerCount = computerService.getSearchComputerCount(search);
            }
        } catch (CDBException e) {
            cpuList = new ArrayList<>();
            totalPages = 0L;
            computerCount = 0L;
        }
        
        List<ComputerDTO> dtoList = ComputerDTOMapper.createDTOList(cpuList);

        ModelAndView modelAndView = new ModelAndView("dashboard");
        setModelAndView(modelAndView, page, search, dtoList,totalPages, computerCount);

        return modelAndView;
    }

    public void setModelAndView(ModelAndView modelAndView, int page, String search, List<ComputerDTO> dtoList, Long totalPages, Long computerCount) {
        modelAndView.addObject("page", page);
        modelAndView.addObject("search", search);
        modelAndView.addObject("dtoList", dtoList);
        modelAndView.addObject("totalPages", totalPages);
        modelAndView.addObject("computerCount", computerCount);
    }

}