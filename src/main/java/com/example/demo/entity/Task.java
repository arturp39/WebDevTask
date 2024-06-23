package com.example.demo.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Task {
    public enum Status {
        NOT_STARTED, IN_PROGRESS, COMPLETED
    }
    private static final Map<Status, String> STATUS_MAP = new HashMap<>();

    static {
        STATUS_MAP.put(Status.NOT_STARTED, "Not Started");
        STATUS_MAP.put(Status.IN_PROGRESS, "In Progress");
        STATUS_MAP.put(Status.COMPLETED, "Completed");
    }
    private int id;
    private String title;
    private String description;
    private Date dueDate;
    private Status status;
    public Task() {
    }

    public Task(int id, String title, String description, Date dueDate, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Task(String title, String description, Date dueDate, Status status) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public String getUserFriendlyStatus() {
        return STATUS_MAP.get(this.status);
    }
}
