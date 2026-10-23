package com.yuri.taskmanager.application;

import com.yuri.taskmanager.application.input.UpdateTaskInput;
import com.yuri.taskmanager.application.output.TaskOutput;
import com.yuri.taskmanager.domain.TaskId;
import com.yuri.taskmanager.domain.TaskNotFoundException;
import com.yuri.taskmanager.domain.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateTaskUseCase {
private final TaskRepository repository;

    public UpdateTaskUseCase(TaskRepository repository) {
       this.repository = repository;
    }

    public TaskOutput execute(TaskId id, UpdateTaskInput input){
        var task = repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        task.update(input.name(), input.description(), input.status());
        var updated = repository.save(task);
        return TaskOutput.from(updated);
    }
}
