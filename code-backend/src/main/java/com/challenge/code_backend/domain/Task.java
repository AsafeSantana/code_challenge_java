package com.challenge.code_backend.domain;

import jakarta.persistence.*;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    private String description;

    private boolean favorite;

    private String color;

    private boolean completed = false;
}
