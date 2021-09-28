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

    //initialize faux db with tasks
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

    /**
     * Returns all tasks in the list
     *
     * @return list of tasks
     */
    public List<Task> fetchAllTasks() {
        return tasks;
    }

    /**
     * Saves a new task or overwrites an existing one into the repo
     *
     * @param task a new or updated task from client to add or update in the repo
     * @return task that was newly created/updated
     */
    public Task save(Task task) {
        Task existingTask = findById(task.getId());
        if (existingTask != null) {
            tasks.set(tasks.indexOf(existingTask), task);
        } else {
            deleteById(task.getId());
            tasks.add(task);
        }

        return task;
    }

    /**
     * Returns task found by id from repo
     *
     * @param id Integer to identify task with
     * @return found task
     */
    public Task findById(int id) {
        Task foundTask = null;

        for (Task task : tasks) {
            if (task.getId() == id) foundTask = task;
        }
        return foundTask;
    }

    /**
     * Delete task from repo
     *
     * @param id Integer to identify which task to delete
     * @return whether task was removed or not
     */
    public Boolean deleteById(int id) {
        return tasks.remove(findById(id));
    }
}

