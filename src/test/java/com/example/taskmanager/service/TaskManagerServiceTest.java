package com.example.taskmanager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.repository.TaskManagerRepository;

@ExtendWith(MockitoExtension.class)
class TaskManagerServiceTest {

    @Mock
    private TaskManagerRepository repository;

    @InjectMocks
    private TaskManagerService service;

    @Test
    void shouldCreateTaskSuccessfully() {
        Task task = new Task();
        task.setTitle("Learn Spring Boot");
        task.setDueDate(LocalDate.now().plusDays(2));
        task.setCompleted(true);

        when(repository.save(task)).thenReturn(task);

        Task result = service.createTask(task);

        assertNotNull(result);
        assertEquals("Learn Spring Boot", result.getTitle());
        verify(repository).save(task);
    }

    @Test
    void shouldDefaultCompletedToFalseWhenNull() {
        Task task = new Task();
        task.setTitle("Default test");
        task.setDueDate(LocalDate.now().plusDays(3));
        task.setCompleted(null);

        when(repository.save(any(Task.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Task result = service.createTask(task);

        assertFalse(result.getCompleted());
        verify(repository).save(task);
    }
}