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
    private String id;
    private Instant createdDate = Instant.now();
    private Instant updatedDate = Instant.now();

    private String name = "unnamed event";
    @Column(length = 10000)
    private String description = "no description";
    private Instant date = Instant.now();
    private Instant endDate = Instant.now();
    @Enumerated(EnumType.STRING)
    private RecurringTime recurringTime = RecurringTime.ONCE;
    private String userId;

    @PrePersist
    private void ensureId() {
        this.setId(UUID.randomUUID().toString());
    }
}
