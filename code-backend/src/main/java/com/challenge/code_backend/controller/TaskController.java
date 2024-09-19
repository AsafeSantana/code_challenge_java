package com.challenge.code_backend.controller;

import com.challenge.code_backend.domain.Task;
import com.challenge.code_backend.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    //CREATE TASK
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.ok(createdTask);
    }
    //GET ALL
    @GetMapping
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }


}
