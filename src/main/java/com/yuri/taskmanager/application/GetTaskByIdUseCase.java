package com.yuri.taskmanager.application;

import com.yuri.taskmanager.application.output.TaskOutput;
import com.yuri.taskmanager.domain.TaskId;
import com.yuri.taskmanager.domain.TaskNotFoundException;
import com.yuri.taskmanager.domain.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class GetTaskByIdUseCase {
    private final TaskRepository repository;

    public GetTaskByIdUseCase(TaskRepository repository) {
        this.repository = repository;
    }

    public TaskOutput execute(TaskId id){
        return repository.findById(id).map(TaskOutput::from).orElseThrow(() -> new TaskNotFoundException(id));
    }
}
