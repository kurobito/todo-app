package com.dvha.todoappspring.controller;

import com.dvha.todoappspring.model.Task;
import com.dvha.todoappspring.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TodoController {
    private final TodoRepository todoRepository;


    TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * Fetch all tasks from db
     *
     * @return List with all tasks from db
     */
    @GetMapping("api/tasks")
    public ResponseEntity<?> fetchAllTasks() {

        List<Task> tasks = todoRepository.findAll();
        return ResponseEntity.ok(tasks);
    }

    /**
     * Fetch one task based on input id
     *
     * @param id to find task
     * @return task
     */
    @GetMapping("api/tasks/{id}")
    public ResponseEntity<Task> fetchTask(@PathVariable Long id) {
        Task task = todoRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        return ResponseEntity.ok(task);
    }

    /**
     * Create new task
     *
     * @param newTask to create
     * @return HTTP 201 created when task created successfully
     */
    @PostMapping("api/tasks")
    public ResponseEntity<?> createTask(@RequestBody Task newTask) {
        Task createdTask = todoRepository.save(newTask);

        URI uri =
                ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(createdTask.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


    /**
     * Replace/update task with newTask information
     *
     * @param id      to find task to be replaced
     * @param newTask to replace old task with
     * @return updated task
     */
    @PutMapping("api/tasks/{id}")
    public ResponseEntity<?> replaceTask(@PathVariable Long id, @RequestBody Task newTask) {
        Task updatedTask = todoRepository.findById(id).map(task -> {
            task.setName(newTask.getName());
            task.setCompleted(newTask.isCompleted());
            task.setCreatedDate(newTask.getCreatedDate());
            return todoRepository.save(task);
        }).orElseGet(() -> {
            newTask.setId(id);
            return todoRepository.save(newTask);
        });

        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    /**
     * Delete task and send whether it has been successfully deleted or not back to client
     *
     * @param id to find task to be deleted
     * @return delete response
     */
    @DeleteMapping("api/tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        todoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
