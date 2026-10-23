package com.yuri.taskmanager.infrastructure.http.request;

import com.yuri.taskmanager.application.input.CreateTaskInput;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Optional;

public record CreateTaskRequest(
        @NotBlank(message = "Name is mandatory")
        @Size(min = 3, max = 100, message = "Minimum of 1 character and maximum of 100 characters.")
        String name,
        Optional<@Size(max = 500, message = "Maximum of 500 characters.") String> description) {

    public CreateTaskInput toInput(){
        return new CreateTaskInput(name, description);
    }
}
