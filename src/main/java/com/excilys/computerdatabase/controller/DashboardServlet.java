package main.java.com.excilys.computerdatabase.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.com.excilys.computerdatabase.dao.CompanyDAO;
import main.java.com.excilys.computerdatabase.dto.ComputerDTO;
import main.java.com.excilys.computerdatabase.exception.CDBException;
import main.java.com.excilys.computerdatabase.mapper.ComputerDTOMapper;
import main.java.com.excilys.computerdatabase.model.Computer;
import main.java.com.excilys.computerdatabase.service.ComputerService;

/**
 * Servlet implementation class DashboardServlet.
 */
@WebServlet(urlPatterns = { "/dashboard", "/index" })
public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ComputerService computerService;
    private final ComputerDTOMapper computerDTOMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardServlet.class);
    

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
        computerService = new ComputerService();
        computerDTOMapper = ComputerDTOMapper.INSTANCE;
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     * @param request the request
     * @param response the response
     * @throws ServletException the exception
     * @throws IOException the exception
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int page = pageToDisplay(request);

        List<Computer> cpuList = computerService.getComputerList(page);

        List<ComputerDTO> dtoList = computerDTOMapper.createDTOList(cpuList);

        Long totalPages, computerCount;
        try {
            totalPages = computerService.getComputerPageCount();
            computerCount = computerService.getComputerCount();
        } catch (CDBException e) {
            LOGGER.error(e.getMessage());
            totalPages = 0L;
            computerCount = 0L;
        }

        request.setAttribute("page", page);
        request.setAttribute("dtoList", dtoList);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("computerCount", computerCount);

        this.getServletContext().getRequestDispatcher("/views/pages/dashboard.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     * @param request the request
     * @param response the response
     * @throws ServletException the exception
     * @throws IOException the exception
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private int pageToDisplay(HttpServletRequest request) {
        int page;
        String pageString = request.getParameter("page");
        if (pageString == null) {
            page = 1;
        } else {
            page = Integer.parseInt(pageString);
        }
        if (page < 1) {
            page = 1;
        }
        return page;
    }

}
