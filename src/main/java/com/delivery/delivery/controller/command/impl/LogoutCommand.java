package com.delivery.delivery.controller.command.impl;

import com.delivery.delivery.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        return "redirect:/delivery_war_exploded/";
    }
}
