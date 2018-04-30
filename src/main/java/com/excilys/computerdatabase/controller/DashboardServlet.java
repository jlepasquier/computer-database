package main.java.com.excilys.computerdatabase.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.excilys.computerdatabase.dto.ComputerDTO;
import main.java.com.excilys.computerdatabase.mapper.DashboardDTOMapper;
import main.java.com.excilys.computerdatabase.model.Computer;
import main.java.com.excilys.computerdatabase.service.ComputerService;

/**
 * Servlet implementation class DashboardServlet.
 */
@WebServlet(urlPatterns = { "/dashboard" })
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
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
        ComputerService cs = new ComputerService();
        List<Computer> cpuList = cs.getComputerList(0);
        List<ComputerDTO> dtoList = DashboardDTOMapper.INSTANCE.createDTOList(cpuList);
        int computerCount = cs.getComputerCount();
        
        request.setAttribute("computerCount", computerCount);
        request.setAttribute("dtoList", dtoList);
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

}
