package com.dvha.todoappspring.repository;

import com.dvha.todoappspring.domain.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class TodoRepository {
    private Integer idCounter = 0;
    private List<Task> tasks = new ArrayList<>();

    {
        Stream.of("Eat", "Sleep", "Repeat").forEach(name -> {
            Task task = new Task();
            task.setName(name);
            task.setCreatedDate(new Date());
            task.setId(idCounter++);
            task.setCompleted(false);
            tasks.add(task);
        });
    }

    public List<Task> fetchAllTasks() {
        return tasks;
    }

    public Task save(Task task) {
        Task existingTask = findById(task.getId());
        if (existingTask!= null) {
            tasks.set(tasks.indexOf(existingTask), task);
        } else {
            deleteById(task.getId());
            tasks.add(task);
        }

        return task;
    }

    public Task findById(int id) {
        Task foundTask = null;

        for (Task task : tasks) {
            if (task.getId() == id) foundTask = task;
        }
        return foundTask;
    }

    public Task deleteById(int id) {
        Task task = findById(id);
        if (task != null) {
            tasks.remove(task);
            return task;
        }
        return null;
    }
}

