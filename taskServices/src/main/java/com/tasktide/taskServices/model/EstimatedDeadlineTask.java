package com.tasktide.taskServices.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class EstimatedDeadlineTask {
    private String taskId;
    private String name;
    private Instant estimatedDeadline;
    private Priority priority;
    private Integer difficulty;
    private Instant deadline;
    private List<EstimatedDeadlineTask> dependedTasks;
}
