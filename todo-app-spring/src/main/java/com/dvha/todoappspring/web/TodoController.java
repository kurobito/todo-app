package com.dvha.todoappspring.web;

import com.dvha.todoappspring.domain.Task;
import com.dvha.todoappspring.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TodoController {

    @Autowired
    private TodoService todoService;

    //fetch all todoItems from db
    @GetMapping("api/tasks")
    public ResponseEntity<?> fetchAllTasks() {
        List<Task> tasks = todoService.fetchAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("api/tasks")
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        Task newTask = todoService.save(task);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(newTask.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("api/tasks/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody Task task) {
        Task updatedTask = todoService.save(task);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    /**
     * Delete task and send whether it has been successfully deleted or not back to client
     * @param id
     * @return delete response
     */
    @DeleteMapping("api/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id) {
        return (todoService.deleteById(id)) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
