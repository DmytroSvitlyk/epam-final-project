package com.delivery.delivery.controller.filter;

import com.delivery.delivery.controller.command.CommandConstants;
import com.delivery.delivery.controller.command.Path;
import com.delivery.delivery.model.Role;
import com.delivery.delivery.model.User;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"}, filterName = "SecurityFilter")
public class SecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        HttpSession session = req.getSession();
        String command = request.getParameter("command");
        User user = (User)session.getAttribute("user");
        if(user == null) {
            user = new User();
            user.setRole(Role.ANONYMOUS);
            session.setAttribute("user", user);
            res.sendRedirect(Path.REDIRECT_LOGIN.substring(9));
            return;
        }

        if(user.getRole() == Role.ANONYMOUS) {
            if(CommandConstants.commonCommands.contains(command) || CommandConstants.adminCommands.contains(command)) {
                res.sendRedirect(Path.REDIRECT_LOGIN.substring(9));
                return;
            }
        }
        else if(user.getRole() == Role.COMMON) {
            if(CommandConstants.adminCommands.contains(command)) {
                res.sendRedirect(Path.REDIRECT_CABINET.substring(9));
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
