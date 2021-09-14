package com.delivery.delivery.controller.command;

public interface Path {

    String LOGIN_PAGE = "/WEB-INF/jsp/login.jsp";
    String REGISTER_PAGE = "/WEB-INF/jsp/register.jsp";
    String CABINET_PAGE = "/WEB-INF/jsp/cabinet.jsp";
    String HOME_PAGE = "/index.jsp";
    String DIRECTIONS_PAGE = "/WEB-INF/jsp/directions.jsp";
    String ADD_DIRECTION_PAGE = "/WEB-INF/jsp/direction-add.jsp";
    String REDIRECT_CABINET = "redirect:cabinet?command=get-cabinet-page";
    String REDIRECT_HOME = "redirect:home?command=get-home-page";
    String REDIRECT_LOGIN = "redirect:login?command=get-login-page";
    String REDIRECT_REGISTER = "redirect:register?command=get-register-page";
    String REDIRECT_DIRECTIONS = "redirect:directions?command=get-directions-page";

}
