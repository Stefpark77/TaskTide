package com.tasktide.taskServices.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    private String id;
    private Instant createdDate = Instant.now();
    private Instant updatedDate = Instant.now();

    private String name = "unnamed task";
    @Column(length = 10000)
    private String description = "no description";
    private Integer progress = 0;
    private Integer difficulty = 0;
    private Instant deadline = null;

    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.MEDIUM;
    @Enumerated(EnumType.STRING)
    private Stage stage = Stage.TO_DO;

    private String userId;
    private String projectId;

    @PrePersist
    private void ensureId() {
        this.setId(UUID.randomUUID().toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdDate, updatedDate, name, description, progress, difficulty, deadline, priority, stage, userId, projectId);
    }
}
