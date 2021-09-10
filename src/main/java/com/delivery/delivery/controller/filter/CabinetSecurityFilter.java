package com.delivery.delivery.controller.filter;

import com.delivery.delivery.controller.command.CommandConstants;
import com.delivery.delivery.model.Role;
import com.delivery.delivery.model.User;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"}, filterName = "CabinetSecurityFilter")
public class CabinetSecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse res = (HttpServletResponse)response;
        HttpServletRequest req = (HttpServletRequest)request;
        String command = request.getParameter("command");
        User user = (User)req.getSession().getAttribute("user");
        if(command.equals(CommandConstants.GET_CABINET_PAGE)) {
            if(user == null || user.getRole() == Role.ANONYMOUS) {
                res.sendRedirect("redirect:/delivery_war_exploded/login?command=get-login-page");
            }
        }
        chain.doFilter(request, response);
    }
}
