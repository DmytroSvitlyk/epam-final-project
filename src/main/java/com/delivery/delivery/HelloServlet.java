package com.delivery.delivery;

import com.delivery.delivery.dao.mysql.MySqlDAOFactory;
import com.delivery.delivery.model.Order;
import com.delivery.delivery.model.Role;
import com.delivery.delivery.model.User;
import com.delivery.delivery.service.OrderService;
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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Order order = OrderService.getInstance().getOrderByUserId(2).get(0);
        out.println(order.getId() + "<br>");
        out.println(order.getUser().getLogin() + "<br>");
        out.println(order.getDirection().getDepotFrom().getName()+ "<br>");
        out.println(order.getDirection().getDepotTo().getName()+ "<br>");
        out.println(order.getAdditional().getGoalDate() + "<br>");
        out.println(order.getCargo().getWeight() + "<br>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    public void destroy() {
    }
}