package com.example.demo.command;

import com.example.demo.command.impl.user.RegisterCommand;
import com.example.demo.command.impl.user.LoginCommand;
import com.example.demo.command.impl.user.DeleteUserCommand;
import com.example.demo.command.impl.user.LogoutCommand;
import com.example.demo.command.impl.task.AddTaskCommand;
import com.example.demo.command.impl.task.ListTasksCommand;
import com.example.demo.command.impl.task.DeleteTaskCommand;
import com.example.demo.command.impl.task.UpdateTaskTitleCommand;
import com.example.demo.command.impl.task.UpdateTaskDescriptionCommand;
import com.example.demo.command.impl.task.UpdateTaskDueDateCommand;
import com.example.demo.command.impl.task.UpdateTaskStatusCommand;

public enum CommandType {
    REGISTER(new RegisterCommand()),
    LOGIN(new LoginCommand()),
    DELETE_USER(new DeleteUserCommand()),
    LOGOUT(new LogoutCommand()),
    ADD_TASK(new AddTaskCommand()),
    LIST_TASKS(new ListTasksCommand()),
    DELETE_TASK(new DeleteTaskCommand()),
    UPDATE_TASK_TITLE(new UpdateTaskTitleCommand()),
    UPDATE_TASK_DESCRIPTION(new UpdateTaskDescriptionCommand()),
    UPDATE_TASK_DUE_DATE(new UpdateTaskDueDateCommand()),
    UPDATE_TASK_STATUS(new UpdateTaskStatusCommand());

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public static Command define(String commandName) {
        try {
            return CommandType.valueOf(commandName.toUpperCase()).getCommand();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
