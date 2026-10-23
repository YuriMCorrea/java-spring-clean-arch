package com.yuri.taskmanager.domain;

import org.springframework.util.Assert;

import java.util.Optional;

public class Task {
    private TaskId taskId;
    private String name;
    private Optional<String> description;
    private TaskStatus status;

    public Task(String name, Optional<String> description) {
        Assert.notNull(name, "Task name is mandatory.");

        this.taskId = new TaskId();
        this.name = name;
        this.description = description;
        this.status = TaskStatus.TO_DO;
    }

    public void update (Optional<String> name, Optional<String> description, Optional<TaskStatus> status){
        name.ifPresent(this::setName);
        description.ifPresent(d -> setDescription(Optional.of(d)));
        status.ifPresent(this::setStatus);
    }

    public TaskId getTaskId() {
        return taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public void setDescription(Optional<String> description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
