package com.delivery.delivery.controller.command.impl;

import com.delivery.delivery.controller.command.Command;
import com.delivery.delivery.controller.command.Path;
import com.delivery.delivery.model.Direction;
import com.delivery.delivery.service.DirectionService;
import com.delivery.delivery.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteDirectionCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        DirectionService service = DirectionService.getInstance();
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            service.deleteDirection(new Direction(id));
            request.removeAttribute("error");
            return Path.REDIRECT_DIRECTIONS;
        } catch (ServiceException e) {
            request.setAttribute("error", "Unable to delete direction");
            return Path.DIRECTIONS_PAGE;
        }
    }
}
