package com.yuri.taskmanager.infrastructure.http.request;

import com.yuri.taskmanager.application.input.UpdateTaskInput;
import com.yuri.taskmanager.domain.TaskStatus;

import java.util.Optional;

public record UpdateTaskRequest(Optional<String> name, Optional<String> description, Optional<String> status) {
    public UpdateTaskInput toInput(){
        return new UpdateTaskInput(name, description, status.map(TaskStatus::valueOf));
    }
}
