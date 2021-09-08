package com.delivery.delivery.controller.command;

import com.delivery.delivery.controller.command.impl.LanguageChangeCommand;
import com.delivery.delivery.controller.command.impl.UnknownCommand;

public class CommandController {

    public Command getCommand(String command) {
        switch (command) {
            case "localeChange" : return new LanguageChangeCommand();
            default: return new UnknownCommand();
        }
    }

}
