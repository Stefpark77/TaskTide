package com.tasktide.userServices.model;

import com.tasktide.userServices.security.model.Role;
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
@Table(name = "users")
public class User {
    @Id
    private String id;
    private Instant createdDate = Instant.now();
    private Instant updatedDate = Instant.now();


    private String username;
    private String hashedPassword;
    private String firstName = "User";
    private String lastName;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ADMIN;

    @PrePersist
    private void ensureId() {
        this.setId(UUID.randomUUID().toString());
    }

}
