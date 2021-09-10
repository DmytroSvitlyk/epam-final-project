package com.delivery.delivery.controller.command.impl;

import com.delivery.delivery.controller.command.Command;
import com.delivery.delivery.model.Role;
import com.delivery.delivery.model.User;
import com.delivery.delivery.service.ServiceException;
import com.delivery.delivery.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            UserService service = UserService.getInstance();
            User user = new User();
            user.setFirstName(request.getParameter("fName"));
            user.setSecondName(request.getParameter("sName"));
            user.setLogin(request.getParameter("login"));
            user.setPassword(request.getParameter("password"));
            user.setPhone(request.getParameter("phone"));
            user.setEmail(request.getParameter("email"));
            user.setRole(Role.COMMON);
            user = service.register(user);
            request.getSession().setAttribute("user", user);
            request.getSession().removeAttribute("error");
            return "redirect:delivery_war_exploded/cabinet?command=get-cabinet-page";
        } catch (ServiceException e) {
            request.getSession().setAttribute("error", "Failed To Register");
            return "/WEB-INF/jsp/register.jsp";
        }

    }
}
