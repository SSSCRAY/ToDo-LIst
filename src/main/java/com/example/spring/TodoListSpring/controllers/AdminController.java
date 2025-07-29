package com.example.spring.TodoListSpring.controllers;

import com.example.spring.TodoListSpring.service.AdminService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/tasks")
    public String allTasks(Model model) {
        model.addAttribute("tasks", adminService.getAllTasks());
        return "admin/tasks";
    }

    @GetMapping("/tasks/{id}")
    public String showTask(@PathVariable("id") Long id, Model model) {
        model.addAttribute("task", adminService.findById(id));

        return "admin/show_task";
    }






}
