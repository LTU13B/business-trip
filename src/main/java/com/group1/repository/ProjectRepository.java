package com.group1.repository;

import org.springframework.data.repository.CrudRepository;

import com.group1.model.Project;

public interface ProjectRepository extends CrudRepository<Project, Integer> {

}
