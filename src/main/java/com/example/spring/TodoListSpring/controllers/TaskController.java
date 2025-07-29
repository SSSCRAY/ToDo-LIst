package com.example.spring.TodoListSpring.controllers;


import com.example.spring.TodoListSpring.models.Task;
import com.example.spring.TodoListSpring.models.User;
import com.example.spring.TodoListSpring.repositories.TaskRepository;
import com.example.spring.TodoListSpring.security.CustomUserDetails;
import com.example.spring.TodoListSpring.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskRepository taskRepository;


    @Autowired
    public TaskController(TaskService taskService, TaskRepository taskRepository) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }


    @GetMapping()
    public String index(Model model) {
        User currentUser = getCurrentUser();
        model.addAttribute("tasks", taskService.findByUser(currentUser));

        return "tasks/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("task", taskService.findByOne(id));

        return "tasks/show";
    }

    @GetMapping("/new")
    public String newTask(@ModelAttribute("task") Task task) {

        return "tasks/new";
    }

    @PostMapping
    public String create(@ModelAttribute("task") Task task) {
        task.setUser(getCurrentUser());
        taskService.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("task", taskService.findByOne(id));

        return "tasks/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("task") Task task) {
        taskService.update(id, task);

        return "redirect:/tasks";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        taskService.deleteTaskById(id);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/toggle")
    public String taskCompleted(@PathVariable("id") Long id) {

        taskService.toggleCompleted(id);
        return "redirect:/tasks";
    }

    // метод для привязки пользователей
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUser();
    }

}
