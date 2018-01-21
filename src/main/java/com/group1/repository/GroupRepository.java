package com.group1.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.group1.model.Employee;
import com.group1.model.Group;

public interface GroupRepository extends CrudRepository<Group, Integer> {
	Set<Group> findByLeader(Employee emp);
	
	Set<Group> findByAccountant(Employee emp);
}
