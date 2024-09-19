package com.challenge.code_backend.services;

import com.challenge.code_backend.domain.Task;
import com.challenge.code_backend.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    //UPDATE TASK
    public Task updateTask(String id, Task taskDetails){
        Task task = getTaskById(id);
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setFavorite(taskDetails.isFavorite());
        task.setColor(taskDetails.getColor());
        task.setCompleted(taskDetails.isCompleted());
        return taskRepository.save(task);
    }


    public Task getTaskById(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }

    //DELETE TASK
    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }

    //GET FAVORITES
    public List<Task> getFavoriteTasks(){
        return  taskRepository.findAllByFavorite(true);
    }

    //GET BY COLOR
    public List<Task> getTaskByColor(String color){
        return taskRepository.findAllByColor(color);
    }

    //PUT ONLY COLOR
    public Task updateTaskColor(String id, String newColor){
        Optional<Task> taskOptional = taskRepository.findById(id);
        if(taskOptional.isPresent()){
            Task task = taskOptional.get();
            task.setColor(newColor);
            return taskRepository.save(task);
        }
        return null;
    }
}
