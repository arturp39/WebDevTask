package com.example.demo.command;

import com.example.demo.command.impl.*;

public enum CommandType {
    REGISTER(new RegisterCommand()),
    DELETE_USER(new DeleteUserCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    DEFAULT(new DefaultCommand()),
    ADD_IMAGE(new AddImageCommand()),
    DELETE_IMAGE(new DeleteImageCommand()),
    LIST_IMAGES(new ListImagesCommand()),
    CHANGE_IMAGE_NAME(new ChangeImageNameCommand())/*,
    UPDATE_USER(new UpdateUserCommand())*/;

    Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command define(String commandStr) {
        try {
            return CommandType.valueOf(commandStr.toUpperCase()).command;
        } catch (IllegalArgumentException e) {
            return null; // or a default command
        }
    }
}
