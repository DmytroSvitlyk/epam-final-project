package com.delivery.delivery;

import com.delivery.delivery.dao.mysql.MySqlDAOFactory;
import com.delivery.delivery.model.Order;
import com.delivery.delivery.model.Role;
import com.delivery.delivery.model.User;
import com.delivery.delivery.service.ServiceException;
import com.delivery.delivery.service.UserService;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));
        user.setFirstName(request.getParameter("fname"));
        user.setSecondName(request.getParameter("sname"));
        user.setEmail(request.getParameter("email"));
        user.setPhone(request.getParameter("phone"));
        user.setRole(Role.COMMON);
        try {
            if (UserService.getInstance().register(user) != null) {
                out.println("User " + user.getLogin() + " is registered and has id " + user.getId());
            }
            else {
                throw new ServiceException("Some error");
            }
        } catch (ServiceException e) {
            request.setAttribute("error", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException servletException) {
                servletException.printStackTrace();
            }
        }
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public void destroy() {
    }
}