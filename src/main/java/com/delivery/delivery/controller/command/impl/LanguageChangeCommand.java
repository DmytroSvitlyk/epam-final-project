package com.delivery.delivery.controller.command.impl;

import com.delivery.delivery.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LanguageChangeCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String newLang = request.getParameter("lang");
        request.getSession().setAttribute("lang", newLang);
        return "/index.jsp";
    }

}
