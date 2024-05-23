package com.tasktide.projectServices.repository.interfaces;

import com.tasktide.projectServices.model.Project;
import org.springframework.data.repository.CrudRepository;

public interface IProjectRepository extends CrudRepository<Project, String> {
}
