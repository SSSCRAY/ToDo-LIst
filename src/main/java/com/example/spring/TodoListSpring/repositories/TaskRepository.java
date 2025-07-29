package com.example.spring.TodoListSpring.repositories;

import com.example.spring.TodoListSpring.models.Task;
import com.example.spring.TodoListSpring.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);


}
