package com.challenge.code_backend.services;

import com.challenge.code_backend.domain.Task;
import com.challenge.code_backend.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // método auxiliar de criação de task
    private Task createTask(String id, String title,String description, Boolean favorite, String color) {
        Task task = new Task();
        task.setId(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setFavorite(favorite);
        task.setColor("color");
        return task;
    }

    @Test
    void testCreateTask() {
        Task task = createTask(null, "Teste Task","Task Descrição",false,"#ffffff");

        when(taskRepository.save(task)).thenReturn(task);

        Task createdTask = taskService.createTask(task);

        assertNotNull(createdTask);
        assertEquals("Teste Task", createdTask.getTitle());
        assertEquals("Task Descrição", createdTask.getDescription());
        assertFalse(createdTask.isFavorite());
        assertEquals("#ffffff", createdTask.getColor());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testGetAllTasks() {
        Task task1 = createTask("1", "Teste Task", "Descrição 1",false,"#ffffff");
        Task task2 = createTask("2", "Teste Task 2","Descrição 2", false, "#ffffff");

        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<Task> tasks = taskService.getAllTasks();

        assertEquals(2, tasks.size(), "Deve retornar 2 tarefas");
        //System.out.println("Teste de sucesso: O número de tarefas é 2.");
        assertEquals("Teste Task", tasks.get(0).getTitle());
        assertEquals("Teste Task 2", tasks.get(1).getTitle());

        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testUpdateTask() {
        Task existingTask = createTask("1", "Teste Task","Descrição", false, "#ffffff");
        Task taskDetails = createTask(null, "New Task","Nova descrição", false, "#ffffff");

        when(taskRepository.findById("1")).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(existingTask);

        Task updatedTask = taskService.updateTask("1", taskDetails);

        assertNotNull(updatedTask);
        assertEquals("New Task", updatedTask.getTitle());
        assertEquals("Nova descrição", updatedTask.getDescription());
        verify(taskRepository, times(1)).findById("1");
        verify(taskRepository, times(1)).save(existingTask);
    }

    @Test
    void testGetTaskById() {
        Task task = createTask("1", "Teste Task","Descrição",false,"#ffffff");

        when(taskRepository.findById("1")).thenReturn(Optional.of(task));

        Task foundTask = taskService.getTaskById("1");

        assertNotNull(foundTask);
        assertEquals("Teste Task", foundTask.getTitle());
        verify(taskRepository, times(1)).findById("1");
    }

    @Test
    void testGetTaskById_NotFound() {
        when(taskRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            taskService.getTaskById("1");
        });

        verify(taskRepository, times(1)).findById("1");
    }

    @Test
    void testDeleteTask() {
        taskService.deleteTask("1");
        verify(taskRepository, times(1)).deleteById("1");
    }

    @Test
    void testGetFavoritesTasks() {
        Task task1 = createTask("1", "Favorita 1","Descrição 1", true, "#ffffff");
        task1.setFavorite(true);
        Task task2 = createTask("2", "Favorita 2","Descrição 2", true, "#ffffff");
        task2.setFavorite(true);

        when(taskRepository.findAllByFavorite(true)).thenReturn(Arrays.asList(task1, task2));

        List<Task> favoriteTasks = taskService.getFavoriteTasks();

        assertEquals(2, favoriteTasks.size());
        verify(taskRepository, times(1)).findAllByFavorite(true);
    }

    @Test
    void testUpdateTaskColor() {
        Task task = createTask("1", "Teste Task","Descrição", false, "#ffffff");
        task.setColor("#ffffff");

        when(taskRepository.findById("1")).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        Task updatedTask = taskService.updateTaskColor("1", "#aaaaaa");

        assertNotNull(updatedTask);
        assertEquals("#aaaaaa", updatedTask.getColor());
        verify(taskRepository, times(1)).findById("1");
        verify(taskRepository, times(1)).save(task);
    }
}
