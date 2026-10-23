package com.yuri.taskmanager.infrastructure;

import com.yuri.taskmanager.application.CreateTaskUsecase;
import com.yuri.taskmanager.infrastructure.http.request.CreateTaskRequest;
import com.yuri.taskmanager.infrastructure.http.response.TaskResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Target;

@RequestMapping("api/v1/tasks")
@RestController
public class TaskController {
    private final CreateTaskUsecase createTaskUsecase;


    public TaskController(CreateTaskUsecase createTaskUsecase) {
        this.createTaskUsecase = createTaskUsecase;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@RequestBody CreateTaskRequest request){
        var output = createTaskUsecase.execute(request.toInput());
        return ResponseEntity.ok(TaskResponse.from(output));
    }
}
