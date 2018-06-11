package com.excilys.computerdatabase.controller;

import static com.excilys.computerdatabase.controller.enums.UserMessage.CREATION_FAIL;
import static com.excilys.computerdatabase.controller.enums.UserMessage.CREATION_SUCCESS;
import static com.excilys.computerdatabase.controller.enums.UserMessage.DELETION_FAIL;
import static com.excilys.computerdatabase.controller.enums.UserMessage.DELETION_SUCCESS;
import static com.excilys.computerdatabase.controller.enums.UserMessage.UPDATE_FAIL;
import static com.excilys.computerdatabase.controller.enums.UserMessage.UPDATE_SUCCESS;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.excilys.computerdatabase.controller.enums.UserMessage;
import com.excilys.computerdatabase.dto.CompanyDTO;
import com.excilys.computerdatabase.dto.ComputerDTO;
import com.excilys.computerdatabase.mapper.CompanyDTOMapper;
import com.excilys.computerdatabase.mapper.ComputerDTOMapper;
import com.excilys.computerdatabase.model.CDBException;
import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;

@Controller
@RequestMapping("/")
public class ComputerController {

    private ComputerService computerService;
    private CompanyService companyService;

    private static final String DEFAULT_PAGE = "1";
    private static final String DEFAULT_SEARCH = "";

    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerController.class);

    public ComputerController(ComputerService computerService, CompanyService companyService) {
        super();
        this.computerService = computerService;
        this.companyService = companyService;
        LOGGER.debug("Instanciated Computer Controller");
    }

    @GetMapping(path = "/login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    private ModelAndView getDashboard(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(value = "search", defaultValue = DEFAULT_SEARCH) String search) {
        LOGGER.debug("Get Dashboard");
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

    @RequestMapping(value = "/dashboard", method = RequestMethod.POST)
    private ModelAndView postDashboard(@ModelAttribute("selection") String ids, BindingResult bindingResult,
            RedirectAttributes attributes) {
        ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
        try {
            if (computerService.deleteComputers(ids)) {
                setSuccessAttributes(attributes, DELETION_SUCCESS);
            } else {
                setFailureAttributes(attributes, DELETION_FAIL.toString());
            }
        } catch (CDBException e) {
            setFailureAttributes(attributes, DELETION_FAIL + "\n" + e.getMessage());
        }

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
    private ModelAndView postEditComputer(@Valid @ModelAttribute("computerDTO") ComputerDTO computerDto,
            BindingResult bindingResult, RedirectAttributes attributes) {

        ModelAndView modelAndView = new ModelAndView("redirect:/editComputer?id=" + computerDto.getId());

        if (bindingResult.hasErrors()) {
            String error = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage())
                    .collect(Collectors.joining("\n"));
            setFailureAttributes(attributes, error);
            return modelAndView;
        }

        try {
            computerService.updateComputer(ComputerDTOMapper.fromDTO(computerDto));
            setSuccessAttributes(attributes, UPDATE_SUCCESS);
        } catch (CDBException e) {
            setFailureAttributes(attributes, UPDATE_FAIL + "\n" + e.getMessage());
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
    private ModelAndView postAddComputer(@Valid @ModelAttribute("computerDTO") ComputerDTO computerDto,
            BindingResult bindingResult, RedirectAttributes attributes) {

        ModelAndView modelAndView = new ModelAndView("redirect:/addComputer");

        if (bindingResult.hasErrors()) {
            String error = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage())
                    .collect(Collectors.joining("\n"));
            setFailureAttributes(attributes, error);
            return modelAndView;
        }
        try {
            computerService.createComputer(ComputerDTOMapper.fromDTO(computerDto));
            setSuccessAttributes(attributes, CREATION_SUCCESS);
        } catch (CDBException e) {
            e.printStackTrace();
            setFailureAttributes(attributes, CREATION_FAIL + "\n" + e.getMessage());
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
