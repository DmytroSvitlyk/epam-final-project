package com.delivery.delivery.controller.command.impl;

import com.delivery.delivery.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetRegisterPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "/WEB-INF/jsp/register.jsp";
    }
}
