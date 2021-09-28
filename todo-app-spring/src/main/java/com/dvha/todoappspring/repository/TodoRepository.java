package com.dvha.todoappspring.repository;

import com.dvha.todoappspring.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Task, Long> {

}
