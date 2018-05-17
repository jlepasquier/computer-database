package main.java.com.excilys.computerdatabase.servlet;

import static main.java.com.excilys.computerdatabase.servlet.enums.UserMessage.UPDATE_SUCCESS;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import main.java.com.excilys.computerdatabase.dto.CompanyDTO;
import main.java.com.excilys.computerdatabase.exception.CDBException;
import main.java.com.excilys.computerdatabase.mapper.CompanyDTOMapper;
import main.java.com.excilys.computerdatabase.mapper.ComputerMapper;
import main.java.com.excilys.computerdatabase.model.Company;
import main.java.com.excilys.computerdatabase.model.Computer;
import main.java.com.excilys.computerdatabase.service.CompanyService;
import main.java.com.excilys.computerdatabase.service.ComputerService;;;

/**
 * Servlet implementation class editComputerServlet
 */
@WebServlet("/editComputer")
public class EditComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private  CompanyService companyService;
    @Autowired
    private  ComputerService computerService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputerServlet() {
        //companyService = new CompanyService();
        //computerService = new ComputerService();
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

        try {
            long id = Long.parseLong(request.getParameter("id"));

            Computer computer = computerService.getComputer(id);
            List<Company> companyList = companyService.getCompanyList();
            List<CompanyDTO> companyDtoList = CompanyDTOMapper.createDTOList(companyList);

            request.setAttribute("id", id);
            request.setAttribute("companyList", companyDtoList);
            request.setAttribute("computer", computer);

        } catch (CDBException e) {
            String errorMessage = e.getMessage();
            request.setAttribute("userMessage", errorMessage);
            doGet(request, response);
        }

        this.getServletContext().getRequestDispatcher("/views/pages/editComputer.jsp").forward(request, response);
    }

    /**
     * u
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

            Computer computer = ComputerMapper.createComputer(id, computerName, introduced, discontinued, companyId);
            computerService.updateComputer(computer);
            
            request.setAttribute("success", true);
            request.setAttribute("userMessage", UPDATE_SUCCESS);
            doGet(request, response);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            request.setAttribute("success", false);
            request.setAttribute("userMessage", errorMessage);
            doGet(request, response);
        }
    }

}
