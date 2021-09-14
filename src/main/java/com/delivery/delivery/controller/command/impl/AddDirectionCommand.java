package com.delivery.delivery.controller.command.impl;

import com.delivery.delivery.controller.command.Command;
import com.delivery.delivery.controller.command.Path;
import com.delivery.delivery.model.Depot;
import com.delivery.delivery.model.Direction;
import com.delivery.delivery.service.DepotService;
import com.delivery.delivery.service.DirectionService;
import com.delivery.delivery.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalTime;

public class AddDirectionCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        DirectionService directionService = DirectionService.getInstance();
        DepotService depotService = DepotService.getInstance();
        Direction direction = new Direction();
        try {
            int depotFromId = depotService.getIdByName(request.getParameter("depotFrom"));
            int depotToId = depotService.getIdByName(request.getParameter("depotTo"));
            if (depotFromId == -1) {
                request.setAttribute("error", "Dispatching Depot Doesn`t Exists");
                return Path.ADD_DIRECTION_PAGE;
            }
            if (depotToId == -1) {
                request.setAttribute("error", "Arrive Depot Doesn`t Exists");
                return Path.ADD_DIRECTION_PAGE;
            }
            direction.setDepotFrom(new Depot(depotFromId));
            direction.setDepotTo(new Depot(depotToId));
            direction.setDepartureTime(LocalTime.parse(request.getParameter("depTime")));
            direction.setArriveTime(LocalTime.parse(request.getParameter("arrTime")));
            direction.setRange(Double.parseDouble(request.getParameter("range")));
            directionService.addDirection(direction);
            request.removeAttribute("error");
            return Path.REDIRECT_DIRECTIONS;
        } catch (ServiceException e) {
            return "/WEB-INT/jsp/error.jsp";
        }

    }
}
