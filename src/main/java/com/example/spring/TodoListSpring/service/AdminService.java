package com.example.spring.TodoListSpring.service;

import com.example.spring.TodoListSpring.models.Task;
import com.example.spring.TodoListSpring.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final TaskRepository taskRepository;

    @Autowired
    public AdminService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Одна задача и ее описание
    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Задача не найдена"));

    }






}
