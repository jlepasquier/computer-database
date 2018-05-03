package main.java.com.excilys.computerdatabase.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ComputerService computerService;
    private final CompanyService companyService;
    private final CompanyDTOMapper companyDTOMapper;
    private final ComputerMapper computerMapper;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputerServlet() {
        companyService = new CompanyService();
        computerService = new ComputerService();
        companyDTOMapper = CompanyDTOMapper.INSTANCE;
        computerMapper = ComputerMapper.INSTANCE;
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Company> companyList = companyService.getCompanyList();
        List<CompanyDTO> dtoList = companyDTOMapper.createDTOList(companyList);
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

            Computer computer = computerMapper.createComputer(computerName, introduced, discontinued, companyId);
            computerService.createComputer(computer);

            String path = this.getServletContext().getContextPath();
            response.sendRedirect(path + "/dashboard");
        } catch (Exception e) { // TODO : change to custom exceptions
            String path = this.getServletContext().getContextPath();
            response.sendRedirect(path + "/addComputer");
        }
    }

}
