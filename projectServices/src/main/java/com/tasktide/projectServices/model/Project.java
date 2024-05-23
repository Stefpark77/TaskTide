package com.tasktide.projectServices.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

    @Id
    private String id;
    private Instant createdDate = Instant.now();
    private Instant updatedDate = Instant.now();

    private String name = "unnamed project";
    @Column(length = 10000)
    private String description = "no description";
    private Instant deadline = Instant.now();

    @PrePersist
    private void ensureId() {
        this.setId(UUID.randomUUID().toString());
    }
}
