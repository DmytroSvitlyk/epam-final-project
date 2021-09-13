package com.delivery.delivery.controller.command.impl;

import com.delivery.delivery.controller.command.Command;
import com.delivery.delivery.controller.command.Path;
import com.delivery.delivery.model.Direction;
import com.delivery.delivery.service.DirectionService;
import com.delivery.delivery.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetCabinetPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("list", DirectionService.getInstance().getAllDirections(10, 0));
        }catch (ServiceException e) {
            request.setAttribute("error", e.getMessage());
        }
        return Path.CABINET_PAGE;
    }
}
