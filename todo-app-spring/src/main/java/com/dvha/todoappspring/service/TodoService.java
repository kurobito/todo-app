package com.dvha.todoappspring.service;

import com.dvha.todoappspring.domain.Task;
import com.dvha.todoappspring.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepo;

    public List<Task> fetchAllTasks(){
        return todoRepo.fetchAllTasks();
    }

    public Task save(Task task){
        return todoRepo.save(task);
    }

    public Task deleteById(int id){
        return todoRepo.deleteById(id);
    }
}
