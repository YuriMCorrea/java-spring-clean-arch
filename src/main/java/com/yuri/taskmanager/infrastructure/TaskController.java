package com.yuri.taskmanager.infrastructure;

import com.yuri.taskmanager.application.*;
import com.yuri.taskmanager.domain.TaskId;
import com.yuri.taskmanager.infrastructure.http.request.CreateTaskRequest;
import com.yuri.taskmanager.infrastructure.http.request.UpdateTaskRequest;
import com.yuri.taskmanager.infrastructure.http.response.TaskResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/tasks")
@RestController
public class TaskController {
    private final CreateTaskUsecase createTaskUsecase;
    private final GetTasksUseCase getTasksUseCase;
    private final GetTaskByIdUseCase getTaskByIdUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;


    public TaskController(CreateTaskUsecase createTaskUsecase, GetTasksUseCase getTasksUseCase, GetTaskByIdUseCase getTaskByIdUseCase, DeleteTaskUseCase deleteTaskUseCase, UpdateTaskUseCase updateTaskUseCase) {
        this.createTaskUsecase = createTaskUsecase;
        this.getTasksUseCase = getTasksUseCase;
        this.getTaskByIdUseCase = getTaskByIdUseCase;
        this.deleteTaskUseCase = deleteTaskUseCase;
        this.updateTaskUseCase = updateTaskUseCase;
    }

    @GetMapping
    ResponseEntity<List<TaskResponse>> list(){
        return ResponseEntity.ok(getTasksUseCase.execute().stream().map(TaskResponse::from).toList());
    }

    @GetMapping("/{id}")
    ResponseEntity<TaskResponse> getById(@PathVariable UUID id){
        return ResponseEntity.ok(
                TaskResponse.from(
                        getTaskByIdUseCase.execute(new TaskId(id))));
    }

    @PostMapping
    ResponseEntity<TaskResponse> create(@RequestBody @Valid CreateTaskRequest request){
        var response = TaskResponse.from(createTaskUsecase.execute(request.toInput()));
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(response.id())
                        .toUri())
                .body(response);
    }

    @PatchMapping("{id}")
    ResponseEntity<TaskResponse> update(@PathVariable UUID id, @RequestBody UpdateTaskRequest request){
        return ResponseEntity.ok(TaskResponse.from(updateTaskUseCase.execute(new TaskId(id), request.toInput())));
    }

    @DeleteMapping("{id}")
     ResponseEntity<Object> delete(@PathVariable UUID id){
        deleteTaskUseCase.execute(new TaskId(id));
        return ResponseEntity.noContent().build();
    }
}
