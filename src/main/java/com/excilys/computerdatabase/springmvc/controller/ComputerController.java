package main.java.com.excilys.computerdatabase.springmvc.controller;

import static main.java.com.excilys.computerdatabase.servlet.enums.UserMessage.CREATION_SUCCESS;
import static main.java.com.excilys.computerdatabase.servlet.enums.UserMessage.CREATION_FAIL;
import static main.java.com.excilys.computerdatabase.servlet.enums.UserMessage.DELETION_SUCCESS;
import static main.java.com.excilys.computerdatabase.servlet.enums.UserMessage.DELETION_FAIL;
import static main.java.com.excilys.computerdatabase.servlet.enums.UserMessage.UPDATE_SUCCESS;
import static main.java.com.excilys.computerdatabase.servlet.enums.UserMessage.UPDATE_FAIL;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import main.java.com.excilys.computerdatabase.dto.CompanyDTO;
import main.java.com.excilys.computerdatabase.dto.ComputerDTO;
import main.java.com.excilys.computerdatabase.exception.CDBException;
import main.java.com.excilys.computerdatabase.mapper.CompanyDTOMapper;
import main.java.com.excilys.computerdatabase.mapper.ComputerDTOMapper;
import main.java.com.excilys.computerdatabase.model.Company;
import main.java.com.excilys.computerdatabase.model.Computer;
import main.java.com.excilys.computerdatabase.service.CompanyService;
import main.java.com.excilys.computerdatabase.service.ComputerService;
import main.java.com.excilys.computerdatabase.servlet.enums.UserMessage;

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
    private ModelAndView getDashboard(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(value = "search", defaultValue = DEFAULT_SEARCH) String search) {

        List<Computer> cpuList;
        Long totalPages, computerCount;
        
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
        setModelAndView(modelAndView, page, search, dtoList, totalPages, computerCount);

        return modelAndView;
    }

    @RequestMapping(value = "/editComputer", method = RequestMethod.GET)
    private ModelAndView getEditComputer(@RequestParam(value = "id", required = true) long id) {

        ModelAndView modelAndView = new ModelAndView("editComputer");

        try {
            Computer computer = computerService.getComputer(id);
            
            addCompanyList(modelAndView);
            modelAndView.addObject("id", id);
            modelAndView.addObject("computer", computer);
        } catch (CDBException e) {
            String errorMessage = e.getMessage();
            modelAndView.addObject("userMessage", errorMessage);
        }

        modelAndView.addObject("computerDTO", new ComputerDTO());
        return modelAndView;
    }

    @RequestMapping(value = "/editComputer", method = RequestMethod.POST)
    private ModelAndView postEditComputer(@ModelAttribute("computerDTO") ComputerDTO computerDto, BindingResult binding,
            RedirectAttributes attributes) {

        ModelAndView modelAndView = new ModelAndView("redirect:/editComputer?id=" + computerDto.getId());

        try {
            computerService.updateComputer(ComputerDTOMapper.fromDTO(computerDto));
            setSuccessAttributes(attributes, UPDATE_SUCCESS);
        } catch (CDBException e) {
            setFailureAttributes(attributes, UPDATE_FAIL + "\n" +  e.getMessage());
        }

        return modelAndView;
    }

    @RequestMapping(value = "/addComputer", method = RequestMethod.GET)
    private ModelAndView getAddComputer() {

        ModelAndView modelAndView = new ModelAndView("addComputer");
        addCompanyList(modelAndView);
        modelAndView.addObject("computerDTO", new ComputerDTO());
        return modelAndView;
    }
    
    
    @RequestMapping(value = "/addComputer", method = RequestMethod.POST)
    private ModelAndView postAddComputer(@ModelAttribute("computerDTO") ComputerDTO computerDto, BindingResult binding,
            RedirectAttributes attributes) {

        ModelAndView modelAndView = new ModelAndView("redirect:/addComputer");

        try {
            computerService.createComputer(ComputerDTOMapper.fromDTO(computerDto));
            setSuccessAttributes(attributes, CREATION_SUCCESS);
        } catch (CDBException e) {
            e.printStackTrace();
            setFailureAttributes(attributes, CREATION_FAIL + "\n" +  e.getMessage());
        }

        return modelAndView;
    }
    
    
    private void addCompanyList(ModelAndView modelAndView) {
        List<Company> companyList = companyService.getCompanyList();
        List<CompanyDTO> companyDtoList = CompanyDTOMapper.createDTOList(companyList);
        modelAndView.addObject("companyList", companyDtoList);
    }
    
    private void setSuccessAttributes(RedirectAttributes attributes, UserMessage userMessage) {
        attributes.addFlashAttribute("success", true);
        attributes.addFlashAttribute("userMessage", userMessage);
    }
    
    private void setFailureAttributes(RedirectAttributes attributes, String userMessage) {
        attributes.addFlashAttribute("success", false);
        attributes.addFlashAttribute("userMessage", userMessage);
    }
    
    

    private void setModelAndView(ModelAndView modelAndView, int page, String search, List<ComputerDTO> dtoList,
            Long totalPages, Long computerCount) {
        modelAndView.addObject("page", page);
        modelAndView.addObject("search", search);
        modelAndView.addObject("dtoList", dtoList);
        modelAndView.addObject("totalPages", totalPages);
        modelAndView.addObject("computerCount", computerCount);
    }

}