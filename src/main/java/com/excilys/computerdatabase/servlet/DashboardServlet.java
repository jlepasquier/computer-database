package main.java.com.excilys.computerdatabase.servlet;

import static main.java.com.excilys.computerdatabase.servlet.enums.UserMessage.DELETION_FAIL;
import static main.java.com.excilys.computerdatabase.servlet.enums.UserMessage.DELETION_SUCCESS;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import main.java.com.excilys.computerdatabase.dto.ComputerDTO;
import main.java.com.excilys.computerdatabase.exception.CDBException;
import main.java.com.excilys.computerdatabase.mapper.ComputerDTOMapper;
import main.java.com.excilys.computerdatabase.model.Computer;
import main.java.com.excilys.computerdatabase.service.ComputerService;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardServlet.class);

    private ComputerService computerService;
    
    public DashboardServlet(ComputerService computerService) {
        super();
        this.computerService = computerService;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
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

        String ids = request.getParameter("selection");

        try {
            if (computerService.deleteComputers(ids)) {
                request.setAttribute("success", true);
                request.setAttribute("userMessage", DELETION_SUCCESS);
            } else {
                request.setAttribute("success", false);
                request.setAttribute("userMessage", DELETION_FAIL);
            }
        } catch (CDBException e) {
            LOGGER.error(e.getMessage());
        }

        doGet(request, response);
    }

    private void renderDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = pageStringToInt(request.getParameter("page"));

        List<Computer> cpuList = computerService.getComputerList(page);
        List<ComputerDTO> dtoList = ComputerDTOMapper.createDTOList(cpuList);

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

    private void renderResearchPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = pageStringToInt(request.getParameter("page"));
        String research = request.getParameter("search");

        List<Computer> cpuList = computerService.searchComputer(research, page);
        List<ComputerDTO> dtoList = ComputerDTOMapper.createDTOList(cpuList);

        Long totalPages, computerCount;
        try {
            totalPages = computerService.getSearchComputerPageCount(research);
            computerCount = computerService.getSearchComputerCount(research);
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
