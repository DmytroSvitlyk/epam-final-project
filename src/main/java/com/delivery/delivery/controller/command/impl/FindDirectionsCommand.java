package com.delivery.delivery.controller.command.impl;

import com.delivery.delivery.controller.command.Command;
import com.delivery.delivery.controller.command.Path;
import com.delivery.delivery.model.Direction;
import com.delivery.delivery.service.DirectionService;
import com.delivery.delivery.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class FindDirectionsCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        DirectionService directionService = DirectionService.getInstance();
        request.setAttribute("currentPage", 1);
        String from = request.getParameter("depotFrom");
        String to = request.getParameter("depotTo");
        String sortBy = request.getParameter("sortBy");
        try {
            List<Direction> directionList = directionService.getDirectionsByDepotNames(from, to, sortBy,10, 0);
            request.setAttribute("pageCount", directionService.getPageCount(10));
            request.setAttribute("list", directionList);
        } catch (ServiceException e) {
            return "/WEB-INF/jsp/error.jsp";
        }

        return Path.DIRECTIONS_PAGE;
    }
}
