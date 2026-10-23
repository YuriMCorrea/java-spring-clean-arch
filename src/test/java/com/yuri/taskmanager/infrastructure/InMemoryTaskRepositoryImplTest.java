package com.yuri.taskmanager.infrastructure;

import com.yuri.taskmanager.domain.Task;
import com.yuri.taskmanager.domain.TaskId;
import com.yuri.taskmanager.domain.TaskRepository;
import com.yuri.taskmanager.domain.TaskRepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
public class InMemoryTaskRepositoryImplTest extends TaskRepositoryTest {

    @Override
    protected TaskRepository createReository() {
        return new InMemoryTaskRepositoryImpl();
    }
}
