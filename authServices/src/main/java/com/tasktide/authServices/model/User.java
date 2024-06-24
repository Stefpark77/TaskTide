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
    private String id;
    private String firstName = "User";
    private String lastName;
    private String username;
    private String hashedPassword;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ADMIN;

    private Instant createdDate = Instant.now();
    private Instant updatedDate = Instant.now();

    @PrePersist
    private void ensureId() {
        this.setId(UUID.randomUUID().toString());
    }

}
