package com.tasktide.authServices.model;

import com.tasktide.authServices.security.model.Role;
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
    @Column(name = "id")
    String id;
    String firstName = "User";
    String lastName;
    String username;
    String hashedPassword;
    String email;

    @Enumerated(EnumType.STRING)
    Role role = Role.ADMIN;

    Instant createdDate = Instant.now();
    Instant updatedDate = Instant.now();

    @PrePersist
    private void ensureId() {
        this.setId(UUID.randomUUID().toString());
    }

}
