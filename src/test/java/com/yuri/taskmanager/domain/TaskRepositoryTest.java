package com.yuri.taskmanager.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public abstract class TaskRepositoryTest {

    TaskRepository repository;

    protected abstract TaskRepository createReository();

    @BeforeEach
    public void setUp () {
        this.repository = createReository();
    }

    @Test
    void should_save_and_find_task_by_id(){
        //GIVEN
        var task = new Task("Fazer compras de super mercado", Optional.empty());

        //WHEN
        var saved = repository.save(task);
        Optional<Task> byId = repository.findById(task.getTaskId());

        //THEN
        assertThat(byId.isPresent());
        assertThat(byId.get().getTaskId())
                .isEqualTo(task.getTaskId());
        assertThat(byId.get().getDescription())
                .isEqualTo(task.getDescription());
        assertThat(byId.get().getName())
                .isEqualTo(task.getName());
        assertThat(byId.get().getStatus())
                .isEqualTo(task.getStatus());
    }

    @Test
    void should_find_all_persisted_task(){
        // GIVEN
        var task1 = new Task("Fazer compras de super mercado", Optional.empty());
        var task2 = new Task("Fazer compras de farmacia", Optional.empty());

        // WHEN
        repository.save(task1);
        repository.save(task2);
        List<Task> all = repository.findAll();

        // THEN
        assertThat(all).isNotEmpty();
        assertThat(all.size()).isEqualTo(2);
        assertThat(all).extracting(Task::getTaskId).containsExactlyInAnyOrder(task1.getTaskId(), task2.getTaskId());
        assertThat(all).extracting(Task::getDescription).containsExactlyInAnyOrder(task1.getDescription(), task2.getDescription());
    }

    @Test
    void should_delete_task_by_id(){
        // GIVEN
        var task = new Task("Fazer compras de super mercado", Optional.empty());
        var taskId = task.getTaskId();

        // WHEN
        assertDoesNotThrow(() -> repository.delete(taskId));
        Optional<Task> result = repository.findById(taskId);

        // THEN
        assertThat(result).isEmpty();
    }

    @Test
    void should_return_empty_when_seearching_non_existant_task(){
        // GIVEN
        var nonExistentId = new TaskId();

        // WHEN
        Optional<Task> result = repository.findById(nonExistentId);

        // THEN
        assertThat(result).isEmpty();

    }

    @Test
    void should_update_task_name_description_and_status(){
        // GIVEN
        var task = new Task("Fazer compras de super mercado", Optional.empty());
        var saved = repository.save(task);
        task.setName("Fazer compras de farmacia");
        task.setDescription(Optional.of("Updated"));


        // WHEN
        Optional<Task> result = repository.update(task);

        // THEN
        assertThat(result.isPresent());
        assertThat(result.get().getTaskId())
                .isEqualTo(task.getTaskId());
        assertThat(result.get().getDescription())
                .isEqualTo(task.getDescription());
        assertThat(result.get().getName())
                .isEqualTo(task.getName());
        assertThat(result.get().getStatus())
                .isEqualTo(task.getStatus());
    }

}