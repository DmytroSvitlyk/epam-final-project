package com.delivery.delivery.controller.filter;

import com.delivery.delivery.controller.command.CommandConstants;
import com.delivery.delivery.controller.command.Path;
import com.delivery.delivery.model.Role;
import com.delivery.delivery.model.User;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"}, filterName = "SecurityFilter")
public class CabinetSecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse res = (HttpServletResponse)response;
        HttpServletRequest req = (HttpServletRequest)request;
        String command = request.getParameter("command");
        User user = (User)req.getSession().getAttribute("user");
        if(CommandConstants.GET_CABINET_PAGE.equals(command)) {
            if(user == null || user.getRole() == Role.ANONYMOUS) {
                res.sendRedirect(Path.REDIRECT_LOGIN);
                return;
            }
        }
        else if(CommandConstants.GET_LOGIN_PAGE.equals(command) || CommandConstants.GET_REGISTER_PAGE.equals(command)) {
            if(user != null && user.getRole() != Role.ANONYMOUS) {
                res.sendRedirect(Path.REDIRECT_CABINET);
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
