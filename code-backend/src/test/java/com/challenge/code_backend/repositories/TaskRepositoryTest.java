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

        Task task1 = new Task();
        task1.setTitle("Ir a academia");
        task1.setDescription("Ir a academia 15h");
        task1.setFavorite(true);
        task1.setColor("#fffff");
        entityManager.persist(task1);

        Task task2 = new Task();
        task2.setTitle("Comprar Pão");
        task2.setDescription("Comprar Pão");
        task2.setFavorite(true);
        task2.setColor("#fffff");
        entityManager.persist(task2);

        Task task3 = new Task();
        task3.setTitle("Ia a praça");
        task3.setDescription("Ir a praça");
        task3.setFavorite(false);
        task3.setColor("#fffff");
        entityManager.persist(task3);

        entityManager.flush();

        List<Task> favoriteTasks = taskRepository.findAllByFavorite(true);

        assertEquals(2,favoriteTasks.size(),"Deve conter 2 tarefas favoritas" );
        assertTrue(favoriteTasks.stream().allMatch(Task::isFavorite), "Todas as tarefas devem ser favoritas");

    }
}