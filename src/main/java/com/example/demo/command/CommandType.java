package com.example.demo.command;

import com.example.demo.command.impl.*;

public enum CommandType {
    REGISTER(new RegisterCommand()),
    DELETE_USER(new DeleteUserCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    DEFAULT(new DefaultCommand()),
    ADD_TASK(new AddTaskCommand()),
    DELETE_TASK(new DeleteTaskCommand()),
    LIST_TASKS(new ListTasksCommand()),
    UPDATE_TASK_TITLE(new UpdateTaskTitleCommand()),
    UPDATE_TASK_DESCRIPTION(new UpdateTaskDescriptionCommand()),
    UPDATE_TASK_DUE_DATE(new UpdateTaskDueDateCommand()),
    UPDATE_TASK_STATUS(new UpdateTaskStatusCommand());

    final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command define(String commandStr) {
        try {
            return CommandType.valueOf(commandStr.toUpperCase()).command;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
