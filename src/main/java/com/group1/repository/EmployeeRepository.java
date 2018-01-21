package com.group1.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.group1.model.Employee;
import com.group1.model.Group;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
	Set<Employee> findByGroup(Group group);
}
