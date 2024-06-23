package com.example.demo.entity;

public class Image {
    private int id;
    private String name;
    private String filePath;
    private int userId;

    public Image() {}

    public Image(int id, String name, String filePath, int userId) {
        this.id = id;
        this.name = name;
        this.filePath = filePath;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
