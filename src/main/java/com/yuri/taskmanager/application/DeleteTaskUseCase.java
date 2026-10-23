package com.yuri.taskmanager.application;

import com.yuri.taskmanager.application.output.TaskOutput;
import com.yuri.taskmanager.domain.TaskId;
import com.yuri.taskmanager.domain.TaskNotFoundException;
import com.yuri.taskmanager.domain.TaskRepository;

public class DeleteClassUseCase {
    private final TaskRepository repository;

    public DeleteClassUseCase(TaskRepository repository) {
        this.repository = repository;
    }

    public void execute(TaskId id){
        if (repository.findById(id).isEmpty()) throw new TaskNotFoundException(id);
        repository.delete(id);
    }
}
