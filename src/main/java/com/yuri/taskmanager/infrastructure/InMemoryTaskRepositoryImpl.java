package com.yuri.taskmanager.infrastructure;

import com.yuri.taskmanager.domain.Task;
import com.yuri.taskmanager.domain.TaskId;
import com.yuri.taskmanager.domain.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryTaskRepositoryImpl implements TaskRepository {
    private final Map<TaskId, Task> storage = new HashMap<>();

    @Override
    public Task save(Task task) {
        storage.put(task.getTaskId(), task);
        return task;
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Task> findById(TaskId id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<Task> update(Task task) {
        Optional<Task> result = Optional.ofNullable(storage.get(task.getTaskId()));
        if (result.isEmpty()) throw new RuntimeException("Task not found");
        result.get().setDescription(task.getDescription());
        result.get().setName(task.getName());
        result.get().setStatus(task.getStatus());

        return result;
    }

    @Override
    public void delete(TaskId id) {
        storage.remove(id);
    }
}
