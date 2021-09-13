package com.delivery.delivery.controller.command.impl;

import com.delivery.delivery.controller.command.Command;
import com.delivery.delivery.controller.command.Path;
import com.delivery.delivery.model.User;
import com.delivery.delivery.service.ServiceException;
import com.delivery.delivery.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            UserService service = UserService.getInstance();
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            User user = service.login(login, password);
            request.getSession().setAttribute("user", user);
            request.getSession().removeAttribute("error");
            return Path.REDIRECT_CABINET;
        } catch (ServiceException e) {
            request.getSession().setAttribute("error", e.getMessage());
            return Path.REDIRECT_LOGIN;
        }
    }
}
