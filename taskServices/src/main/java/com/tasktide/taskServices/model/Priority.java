package com.tasktide.taskServices.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Priority {
    HIGH(),
    MEDIUM(),
    LOW(),
}
