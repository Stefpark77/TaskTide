package com.tasktide.calendarServices.model;

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
@Table(name = "events")
public class Event {

    @Id
    @Column(name = "id")
    String id;
    @Column(name = "event_name")
    String name = "unnamed event";
    @Column(name = "event_description")
    String description = "no description";
    @Column(name = "event_date")
    Instant date = Instant.now();
    Boolean everyYear = false;
    Boolean everyMonth = false;
    String userId;
    @Column(name = "created_date")
    Instant createdDate = Instant.now();
    @Column(name = "updated_date")
    Instant updatedDate = Instant.now();

    @PrePersist
    private void ensureId() {
        this.setId(UUID.randomUUID().toString());
    }
}
