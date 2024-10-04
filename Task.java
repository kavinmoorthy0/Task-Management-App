package com.example.taskmanagerapp;

public class Task {
    private int id;
    private String task;
    private String priority;

    public Task(int id, String task, String priority) {
        this.id = id;
        this.task = task;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public String getPriority() {
        return priority;
    }
}
