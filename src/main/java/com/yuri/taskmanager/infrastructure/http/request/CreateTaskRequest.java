package com.yuri.taskmanager.infrastructure.http.request;

import com.yuri.taskmanager.application.input.CreateTaskInput;

import java.util.Optional;

public record CreateTaskRequest(String name, Optional<String> description) {

    public CreateTaskInput toInput(){
        return new CreateTaskInput(name, description);
    }
}
