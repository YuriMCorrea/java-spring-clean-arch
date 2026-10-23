package com.yuri.taskmanager.application;

import com.yuri.taskmanager.application.input.CreateTaskInput;
import com.yuri.taskmanager.application.output.TaskOutput;
import com.yuri.taskmanager.domain.Task;
import com.yuri.taskmanager.domain.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CreateTaskUseCaseTest {

    @Mock
    TaskRepository repository;

    //@Autowired
    @InjectMocks
    CreateTaskUsecase useCase;

    @Test
    void should_create_new_task_successfully(){
        // GIVEN
        var name = "Estudar AI";
        var description = Optional.of("Avançar em Cursor");
        CreateTaskInput input = new CreateTaskInput(name , description);

        // MOCK
        when(repository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        TaskOutput output = useCase.execute(input);

        // THEN
        assertNotNull(output);
        assertNotNull(output.id());
        assertEquals(name, output.name());
        assertEquals(description, output.description());

        // VERIFY
        verify(repository, times(1)).save(any(Task.class));
    }

}