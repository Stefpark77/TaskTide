package com.tasktide.taskServices.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Stage {
    TO_DO(),
    IN_PROGRESS(),
    COMPLETED(),
}
