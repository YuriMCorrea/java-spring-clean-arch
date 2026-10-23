package com.yuri.taskmanager.application.output;

import com.yuri.taskmanager.domain.Task;

import java.util.Optional;

public record TaskOutput(String id, String name, Optional<String> description, String status) {

    public static TaskOutput from (Task task){
        return new TaskOutput(task.getTaskId().toString(),
                task.getName(),
                task.getDescription(),
                task.getStatus().name());
    }
}
