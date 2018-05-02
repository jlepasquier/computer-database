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
import main.java.com.excilys.computerdatabase.model.Company;
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

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputerServlet() {
        companyService = new CompanyService();
        computerService = new ComputerService();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<Company> companyList = companyService.getCompanyList();
        List<CompanyDTO> dtoList = CompanyDTOMapper.INSTANCE.createDTOList(companyList);
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
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
