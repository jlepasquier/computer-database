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
 * Servlet implementation class editComputerServlet
 */
@WebServlet("/editComputer")
public class EditComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CompanyService companyService;
    private ComputerService computerService;
    private ComputerMapper computerMapper;
    private CompanyDTOMapper companyDTOMapper;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputerServlet() {
        companyService = new CompanyService();
        computerService = new ComputerService();
        computerMapper = ComputerMapper.INSTANCE;
        companyDTOMapper = CompanyDTOMapper.INSTANCE;
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
        request.setAttribute("id", request.getParameter("id"));

        this.getServletContext().getRequestDispatcher("/views/pages/editComputer.jsp").forward(request, response);
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
            String id = request.getParameter("id");

            System.out.println(computerName);
            System.out.println(introduced);
            System.out.println(discontinued);
            System.out.println(companyId);
            System.out.println(id);

            Computer computer = computerMapper.createComputer(id, computerName, introduced, discontinued, companyId);
            computerService.updateComputer(computer);

            String path = this.getServletContext().getContextPath();
            response.sendRedirect(path + "/dashboard");
        } catch (Exception e) { // TODO : change to custom exceptions
            String path = this.getServletContext().getContextPath();
            response.sendRedirect(path + "/editComputer");
        }
    }

}
