package main.java.com.excilys.computerdatabase.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ExceptionHandlerServlet
 */
@WebServlet("/ExceptionHandler")
public class ExceptionHandlerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExceptionHandlerServlet() {
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processError(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // Analyze the servlet exception
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
        if (servletName == null) {
            servletName = "Unknown";
        }
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }

        switch (statusCode) {
        case 403:
            this.getServletContext().getRequestDispatcher("/views/errors/403.jsp").forward(request, response);
            break;
        case 404:
            this.getServletContext().getRequestDispatcher("/views/errors/404.jsp").forward(request, response);
            break;
        case 500:
            this.getServletContext().getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
            break;
        default:
            response.setContentType("text/html");

            PrintWriter out = response.getWriter();
            out.write("<html><head><title>Exception/Error Details</title></head><body>");
            if (statusCode != 500) {
                out.write("<h3>Error Details</h3>");
                out.write("<strong>Status Code</strong>:" + statusCode + "<br>");
                out.write("<strong>Requested URI</strong>:" + requestUri);
            } else {
                out.write("<h3>Exception Details</h3>");
                out.write("<ul><li>Servlet Name:" + servletName + "</li>");
                out.write("<li>Exception Name:" + throwable.getClass().getName() + "</li>");
                out.write("<li>Requested URI:" + requestUri + "</li>");
                out.write("<li>Exception Message:" + throwable.getMessage() + "</li>");
                out.write("</ul>");
            }

            out.write("<br><br>");
            out.write("<a href=\"index.html\">Home Page</a>");
            out.write("</body></html>");
            break;
        }

    }
}