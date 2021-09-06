package com.delivery.delivery;

import com.delivery.delivery.dao.mysql.MySqlDAOFactory;
import com.delivery.delivery.model.Order;
import com.delivery.delivery.model.User;
import com.delivery.delivery.service.UserService;

import java.io.*;
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
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        String login = request.getParameter("login");
        out.println(login);
        String password = request.getParameter("password");
        User user = UserService.getInstance().login(login, password);
        if(user == null) {
            response.sendRedirect("index.jsp");
        }
        else {
            out.println("Успішний вхід");
            out.println(user.getFirstName() + " " + user.getSecondName());
            out.println(user.getEmail() + " " + user.getRole());
        }
        out.println("</body></html>");
    }

    public void destroy() {
    }
}