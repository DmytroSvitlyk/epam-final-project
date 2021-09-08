package com.delivery.delivery.controller.filter;

import com.delivery.delivery.model.Role;
import com.delivery.delivery.model.User;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"},filterName = "SetUserFilter")
public class SetUserFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpSession session = ((HttpServletRequest)request).getSession();
        User user = (User)session.getAttribute("user");
        if(user == null) {
            user = new User();
            user.setRole(Role.ANONYMOUS);
            session.setAttribute("user", user);
        }
        chain.doFilter(request, response);
    }
}
