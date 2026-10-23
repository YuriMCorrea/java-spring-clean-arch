package com.yuri.taskmanager.application.input;

import com.yuri.taskmanager.domain.TaskStatus;

import java.util.Optional;

public record UpdateTaskInput(Optional<String> name,
                              Optional<String> description,
                              Optional<TaskStatus> status) {
}
