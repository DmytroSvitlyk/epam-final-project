package com.delivery.delivery.controller.command;

import com.delivery.delivery.controller.command.impl.*;
import com.delivery.delivery.controller.command.impl.UnknownCommand;

public class CommandController {

    public Command getCommand(String command) {
        switch (command) {
            case CommandConstants.LOCALE_CHANGE: return new LanguageChangeCommand();
            case CommandConstants.GET_LOGIN_PAGE: return new GetLoginPageCommand();
            case CommandConstants.GET_REGISTER_PAGE : return new GetRegisterPageCommand();
            case CommandConstants.LOGIN : return new LoginCommand();
            case CommandConstants.REGISTER : return new RegisterCommand();
            case CommandConstants.LOGOUT : return new LogoutCommand();
            case CommandConstants.GET_CABINET_PAGE : return new GetCabinetPageCommand();
            default: return new UnknownCommand();
        }
    }

}
