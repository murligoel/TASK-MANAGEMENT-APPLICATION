package com.example.taskmanager.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.repository.TaskManagerRepository;

@Service
public class TaskManagerService {

    
    @Autowired
    private TaskManagerRepository taskManagerRepository;

    public Task createTask(Task task) {
        if(task.getDueDate() != null) {
            validateDueDate(task.getDueDate());
        }
        if(task.getCompleted() == null) {
            task.setCompleted(false);
        }
        return taskManagerRepository.save(task);
    }

    private void validateDueDate(LocalDate dueDate) {
        if (!dueDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Due date must be in the future");
        }
    }

    public Task getTaskWithId(String id) {
        return taskManagerRepository.findById(Long.parseLong(id)).orElse(null);
    }

    public Task updateTaskWithId(String id, Task task) {
        if(!Objects.isNull(task.getDueDate())) {
            validateDueDate(task.getDueDate());
        }
        Long lId = Long.parseLong(id);
        Task existingTask = taskManagerRepository.findById(Long.parseLong(id)).orElse(null);
        if(existingTask != null) {
            if(!Objects.isNull(task.getTitle())) {
                existingTask.setTitle(task.getTitle());
            }
            if(!Objects.isNull(task.getDescription())) {
                existingTask.setDescription(task.getDescription());
            }
            if(!Objects.isNull(task.getDueDate())) {
                existingTask.setDueDate(task.getDueDate());
            }
            if(!Objects.isNull(task.getCompleted())) {
                existingTask.setCompleted(task.getCompleted());
            }
            taskManagerRepository.save(existingTask);
            return existingTask;
        }
        return null;
    }

    public List<Task> getTasks() {
        return taskManagerRepository.findAll();
    }

    public Long deleteTaskWithId(String id) {
        Task task = taskManagerRepository.findById(Long.parseLong(id)).orElse(null);
        if(task != null) {
            taskManagerRepository.deleteById(Long.parseLong(id));
            return Long.parseLong(id);
        }
        return null;
    }
}