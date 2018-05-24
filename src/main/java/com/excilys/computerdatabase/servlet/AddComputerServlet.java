package main.java.com.excilys.computerdatabase.servlet;

import static main.java.com.excilys.computerdatabase.servlet.enums.UserMessage.CREATION_SUCCESS;
import static main.java.com.excilys.computerdatabase.servlet.enums.UserMessage.CREATION_FAIL;
import static main.java.com.excilys.computerdatabase.servlet.enums.UserMessage.DELETION_SUCCESS;
import static main.java.com.excilys.computerdatabase.servlet.enums.UserMessage.DELETION_FAIL;
import static main.java.com.excilys.computerdatabase.servlet.enums.UserMessage.UPDATE_SUCCESS;
import static main.java.com.excilys.computerdatabase.servlet.enums.UserMessage.UPDATE_FAIL;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import main.java.com.excilys.computerdatabase.dto.CompanyDTO;
import main.java.com.excilys.computerdatabase.mapper.CompanyDTOMapper;
import main.java.com.excilys.computerdatabase.mapper.ComputerMapper;
import main.java.com.excilys.computerdatabase.model.Company;
import main.java.com.excilys.computerdatabase.model.Computer;
import main.java.com.excilys.computerdatabase.service.CompanyService;
import main.java.com.excilys.computerdatabase.service.ComputerService;

/**
 * Servlet implementation class addComputerServlet
 */
//@WebServlet(asyncSupported = false, name = "AddComputerServlet", urlPatterns = { "/addComputer" })
public class AddComputerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private ComputerService computerService;
    private CompanyService companyService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputerServlet(ComputerService computerService, CompanyService companyService) {
        super();
        this.computerService = computerService;
        this.companyService = companyService;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Company> companyList = companyService.getCompanyList();
        List<CompanyDTO> dtoList = CompanyDTOMapper.createDTOList(companyList);
        request.setAttribute("companyList", dtoList);

        this.getServletContext().getRequestDispatcher("/views/pages/addComputer.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String computerName = request.getParameter("computerName");
            String introduced = request.getParameter("introduced");
            String discontinued = request.getParameter("discontinued");
            String companyId = request.getParameter("companyId");

            Computer computer = ComputerMapper.createComputer(computerName, introduced, discontinued, companyId);
            computerService.createComputer(computer);

            request.setAttribute("success", true);
            request.setAttribute("userMessage", CREATION_SUCCESS);
            doGet(request, response);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            request.setAttribute("success", false);
            request.setAttribute("userMessage", errorMessage);
            doGet(request, response);
        }
    }

}
