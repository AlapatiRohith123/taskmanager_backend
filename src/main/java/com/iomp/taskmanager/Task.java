package com.iomp.taskmanager;


import java.util.List;

public class Task {
    private int taskId;
    private String description;
    private int daysRequired;
    private String deadline;
    private List<Integer> dependencies;

    // Getters and Setters
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDaysRequired() {
        return daysRequired;
    }

    public void setDaysRequired(int daysRequired) {
        this.daysRequired = daysRequired;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public List<Integer> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Integer> dependencies) {
        this.dependencies = dependencies;
    }
}
