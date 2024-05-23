package com.tasktide.projectServices.model;

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
@IdClass(ProjectUserId.class)
@Table(name = "project_users")
public class ProjectUser {
    private Instant createdDate = Instant.now();
    private Instant updatedDate = Instant.now();

    @Id
    private String userId;
    @Id
    private String projectId;
}
