package com.yuri.taskmanager.domain;

import org.springframework.util.Assert;

import java.util.UUID;

public record TaskId(UUID id) {
    public TaskId {
        Assert.notNull(id, "Id must not be null.");
    }

    public TaskId(){
        this(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
