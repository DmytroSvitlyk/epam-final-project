package com.delivery.delivery.controller.command.impl;

import com.delivery.delivery.controller.command.Command;
import com.delivery.delivery.controller.command.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetHomePageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return Path.HOME_PAGE;
    }
}
