package com.challenge.code_backend.controller;

import com.challenge.code_backend.domain.Task;
import com.challenge.code_backend.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    //UPDATE TASK
    @PutMapping("/{id}")
    public ResponseEntity<Task>updateTask(@PathVariable String id, @RequestBody Task taskDetails){
        Task updatedTask = taskService.updateTask(id, taskDetails);
        return ResponseEntity.ok(updatedTask);
    }

    //DELETE TASK
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id){
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    //GET FAVORITES
    @GetMapping("/favorites")
    public List<Task> getFavoriteTasks(){
        return taskService.getFavoriteTasks();
    }

    //GET COLOR
    @GetMapping("/color/{color}")
    public List<Task> getTaskByColor(@PathVariable String color){
       return taskService.getTaskByColor(color);
    }

    //UPDATE COLOR
    @PatchMapping("/{id}/color")
    public ResponseEntity<Task> updateTaskColor(@PathVariable String id, @RequestBody String newColor){
        Task updateTask = taskService.updateTaskColor(id, newColor);
        if(updateTask != null){
            return ResponseEntity.ok(updateTask);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}
