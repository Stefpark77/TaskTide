package com.tasktide.taskServices.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(DependencyId.class)
@Table(name = "task_dependencies")
public class TaskDependency {
    private Instant createdDate = Instant.now();
    private Instant updatedDate = Instant.now();

    @Id
    private String taskId;
    @Id
    private String dependsOnId;
}
