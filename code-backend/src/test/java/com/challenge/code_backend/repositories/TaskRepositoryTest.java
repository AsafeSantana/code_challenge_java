package com.challenge.code_backend.repositories;

import com.challenge.code_backend.domain.Task;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TaskRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    TaskRepository taskRepository;

    @Test
    void findAllByFavorite() {

        persistTask("Ir a academia", "Ir a academia 15h", true, "#ffffff");
        persistTask("Comprar Pão", "Comprar Pão", true, "#ffffff");
        persistTask("Ir a praça", "Ir a praça", false, "#ffffff");

        entityManager.flush();

        List<Task> favoriteTasks = taskRepository.findAllByFavorite(true);

        assertEquals(2, favoriteTasks.size(), "Deve conter 2 tarefas favoritas");
        assertTrue(favoriteTasks.stream().allMatch(Task::isFavorite), "Todas as tarefas devem ser favoritas");


    }

    @Test
    void findAllByColor() {

        persistTask("Lavar o carro", "Lavar o carro amanhã", true, "#faffff");
        persistTask("Fazer o lanche", "Fazer o lanche da tarde", true, "#faffff");
        persistTask("Fazer Almoço", "Começar o almoço", false, "#ffffff");

        entityManager.flush();

        List<Task> colorTasks = taskRepository.findAllByColor("#faffff");

        assertEquals(2, colorTasks.size(), "Deve conter 2 tarefas com a cor especificada");
        assertTrue(colorTasks.stream().allMatch(task -> "#faffff".equals(task.getColor())), "Todas as tarefas devem ter a cor especificada");
    }

    private void persistTask(String title, String description, boolean favorite, String color) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setFavorite(favorite);
        task.setColor(color);
        entityManager.persist(task);
    }
}
