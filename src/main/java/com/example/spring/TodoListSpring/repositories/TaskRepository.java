package com.example.spring.TodoListSpring.repositories;

import com.example.spring.TodoListSpring.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {



}
