package com.yuri.taskmanager.domain;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(TaskId id) {
        super(String.format("Task not found with the provided id: %s", id));
    }
}
