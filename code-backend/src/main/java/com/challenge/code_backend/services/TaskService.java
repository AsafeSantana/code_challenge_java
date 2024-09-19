package com.challenge.code_backend.services;

import com.challenge.code_backend.domain.Task;
import com.challenge.code_backend.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;


    //CREATE TASK
    public Task createTask(Task task){
        return taskRepository.save(task);
    }

    //GET ALL
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

}
