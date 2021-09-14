package com.delivery.delivery.controller.command;

import java.util.Arrays;
import java.util.List;

public interface CommandConstants {

    String GET_LOGIN_PAGE = "get-login-page";
    String GET_REGISTER_PAGE = "get-register-page";
    String GET_CABINET_PAGE = "get-cabinet-page";
    String GET_DIRECTION_ADD_PAGE = "get-direction-add-page";
    String LOGOUT = "logout";
    String LOGIN = "login";
    String REGISTER = "register";
    String LOCALE_CHANGE = "locale-change";
    String GET_HOME_PAGE = "get-home-page";
    String GET_DIRECTIONS_PAGE = "get-directions-page";
    String GET_DIRECTIONS = "get-directions";
    String FIND_DIRECTIONS = "find-directions";
    String DIRECTION_ADD = "direction-add";
    String DIRECTION_DELETE = "direction-delete";
    String DIRECTION_ORDER = "direction-direction";

    List<String> commonCommands = Arrays.asList(
            LOGOUT,
            GET_CABINET_PAGE,
            DIRECTION_ORDER
    );

    List<String> adminCommands = Arrays.asList(
            DIRECTION_ADD,
            DIRECTION_DELETE
    );

}
