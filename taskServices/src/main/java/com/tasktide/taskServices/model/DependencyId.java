package com.tasktide.taskServices.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class DependencyId implements Serializable {
     private String taskId;
     private String dependsOnId;
}
