package main.java.com.excilys.computerdatabase.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.com.excilys.computerdatabase.dto.ComputerDTO;
import main.java.com.excilys.computerdatabase.exception.CDBException;
import main.java.com.excilys.computerdatabase.mapper.ComputerDTOMapper;
import main.java.com.excilys.computerdatabase.model.Computer;
import main.java.com.excilys.computerdatabase.service.ComputerService;

/**
 * Servlet implementation class DashboardServlet.
 */
@WebServlet("/dashboard")
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

        String research = request.getParameter("search");
        boolean researchPage = !(research == null || research.equals(""));

        if (researchPage) {
            renderResearchPage(request, response);
        } else {
            renderDashboard(request, response);
        }
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

        String selection = request.getParameter("selection");

        for (String s : selection.split(",")) { //@TODO 
            int id = Integer.parseInt(s);
            try {
                computerService.deleteComputer(id);
            } catch (CDBException e) {
                LOGGER.error(e.getMessage());
            }
        }

        doGet(request, response);
    }

    private void renderDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = pageStringToInt(request.getParameter("page"));

        List<Computer> cpuList = computerService.getComputerList(page);
        List<ComputerDTO> dtoList = computerDTOMapper.createDTOList(cpuList);

        Long totalPages, computerCount;
        try {
            totalPages = computerService.getComputerPageCount();
            computerCount = computerService.getComputerCount();
            request.setAttribute("page", page);
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

    private void renderResearchPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = pageStringToInt(request.getParameter("page"));
        String research = request.getParameter("search");

        List<Computer> cpuList = computerService.searchComputer(research, page);
        List<ComputerDTO> dtoList = computerDTOMapper.createDTOList(cpuList);

        Long totalPages, computerCount;
        try {
            totalPages = computerService.getSearchComputerPageCount(research);
            computerCount = computerService.getSearchComputerCount(research, page);
        } catch (CDBException e) {
            LOGGER.error(e.getMessage());
            totalPages = 0L;
            computerCount = 0L;
        }

        request.setAttribute("page", page);
        request.setAttribute("dtoList", dtoList);
        request.setAttribute("search", research);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("computerCount", computerCount);

        this.getServletContext().getRequestDispatcher("/views/pages/dashboard.jsp").forward(request, response);

    }

    private int pageStringToInt(String pageString) {
        int page;
        if (pageString == null) {
            page = 0;
        } else {
            page = Integer.parseInt(pageString) - 1;
        }
        if (page < 1) {
            page = 0;
        }
        return page;
    }

}
