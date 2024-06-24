package com.tasktide.userServices.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class ProjectUserId implements Serializable {
    private String userId;
    private String projectId;
}
