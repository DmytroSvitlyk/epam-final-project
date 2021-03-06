package com.delivery.delivery.controller.servlet;

import com.delivery.delivery.controller.command.CommandController;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

public class FrontController extends HttpServlet {

    CommandController controller = new CommandController();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String target = controller.getCommand(request.getParameter("command")).execute(request, response);
        if(target.startsWith("redirect:")) {
            response.sendRedirect(target.substring(9));
        }
        else {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(target);
            dispatcher.forward(request, response);
        }
    }

}
