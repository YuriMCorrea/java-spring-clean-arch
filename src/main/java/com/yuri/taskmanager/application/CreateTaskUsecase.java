package com.yuri.taskmanager.application;

import com.yuri.taskmanager.application.input.CreateTaskInput;
import com.yuri.taskmanager.application.output.TaskOutput;
import com.yuri.taskmanager.domain.Task;
import com.yuri.taskmanager.domain.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateTaskUsecase {
    private final TaskRepository repository;

    public CreateTaskUsecase(TaskRepository repository) {
        this.repository = repository;
    }

    public TaskOutput execute(CreateTaskInput input){
        var task = new Task(input.name(), input.description());
        var saved = repository.save(task);
        return TaskOutput.from(saved);
    };


}
