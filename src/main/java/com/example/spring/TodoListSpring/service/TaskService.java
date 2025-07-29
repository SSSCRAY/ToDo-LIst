package com.example.spring.TodoListSpring.service;

import com.example.spring.TodoListSpring.models.Task;
import com.example.spring.TodoListSpring.models.User;
import com.example.spring.TodoListSpring.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Все задачи
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public List<Task> findByUser(User user) {
        return taskRepository.findByUser(user);
    }

    // Одна задача и ее описание
    public Task findByOne(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.orElse(null);
    }

    // Создать задачу
    @Transactional
    public void save(Task task) {
        taskRepository.save(task);
    }


    // Обновить задачу
    @Transactional
    public void update(Long id, Task updateTask) {
        updateTask.setId(id);
        taskRepository.save(updateTask);
    }

    // Удалить задачу
    @Transactional
    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    @Transactional
    public void toggleCompleted(Long id) {
        Task task = findByOne(id);
        if (task != null) {
            task.setCompleted(!task.isCompleted());
            taskRepository.save(task);
        }
    }
}
