package com.yuri.taskmanager.infrastructure.http.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yuri.taskmanager.application.output.TaskOutput;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
public record TaskResponse(String id, String name, String description, String status) {
    public static TaskResponse from(TaskOutput output){
        return new TaskResponse(output.id().toString(),
                output.name(),
                output.description().orElse(null),
                output.status());
    }
}
