package com.challenge.code_backend.controller;

import com.challenge.code_backend.domain.Task;
import com.challenge.code_backend.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    void testCreateTask() throws Exception {
        Task task = new Task();
        task.setTitle("Teste Task");

        when(taskService.createTask(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/api/tasks")
                        .contentType("application/json")
                        .content("{\"title\":\"Teste Task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Teste Task"));

        verify(taskService, times(1)).createTask(any(Task.class));
    }

    @Test
    void testGetAllTasks() throws Exception {
        Task task1 = new Task();
        task1.setTitle("Teste Task");

        Task task2 = new Task();
        task2.setTitle("Teste Task 2");

        when(taskService.getAllTasks()).thenReturn(Arrays.asList(task1, task2));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Teste Task"))
                .andExpect(jsonPath("$[1].title").value("Teste Task 2"));

        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void testUpdateTask() throws Exception {
        Task existingTask = new Task();
        existingTask.setId("1");
        existingTask.setTitle("Teste Task");

        when(taskService.updateTask(eq("1"), any(Task.class))).thenReturn(existingTask);

        mockMvc.perform(put("/api/tasks/1")
                        .contentType("application/json")
                        .content("{\"title\":\"New Task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Teste Task"));

        verify(taskService, times(1)).updateTask(eq("1"), any(Task.class));
    }

    @Test
    void testDeleteTask() throws Exception {
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());

        verify(taskService, times(1)).deleteTask("1");
    }

    @Test
    void testGetFavoriteTasks() throws Exception {
        Task task1 = new Task();
        task1.setFavorite(true);

        Task task2 = new Task();
        task2.setFavorite(true);

        when(taskService.getFavoriteTasks()).thenReturn(Arrays.asList(task1, task2));

        mockMvc.perform(get("/api/tasks/favorites"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", org.hamcrest.Matchers.hasSize(2)));

        verify(taskService, times(1)).getFavoriteTasks();
    }

    @Test
    void testGetTaskByColor() throws Exception {
        String color = "ffffff";
        Task task = new Task();
        task.setColor(color);

        when(taskService.getTaskByColor(color)).thenReturn(Arrays.asList(task));

        mockMvc.perform(get("/api/tasks/color/{color}", color))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].color").value(color));

        verify(taskService, times(1)).getTaskByColor(color);
    }

    @Test
    void testUpdateTaskColor() throws Exception {
        Task task = new Task();
        task.setId("1");
        task.setColor("#ffffff");

        when(taskService.updateTaskColor(eq("1"), any(String.class))).thenReturn(task);

        mockMvc.perform(patch("/api/tasks/1/color")
                        .contentType("text/plain")
                        .content("#aaaaaa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value("#ffffff"));

        verify(taskService, times(1)).updateTaskColor(eq("1"), any(String.class));
    }

    @Test
    void testUpdateTaskColor_NotFound() throws Exception {
        when(taskService.updateTaskColor(eq("1"), any(String.class))).thenReturn(null);

        mockMvc.perform(patch("/api/tasks/1/color")
                        .contentType("text/plain")
                        .content("#aaaaaa"))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).updateTaskColor(eq("1"), any(String.class));
    }
}
