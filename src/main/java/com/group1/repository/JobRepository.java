package com.group1.repository;

import org.springframework.data.repository.CrudRepository;

import com.group1.model.Job;

public interface JobRepository extends CrudRepository<Job, Integer> {

}
