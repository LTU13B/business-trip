package com.group1.repository;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group1.model.Employee;
import com.group1.model.Plan;

@Repository
@Transactional
public interface PlanRepository extends CrudRepository<Plan, Integer> {
	Set<Plan> findByEmp(Employee emp);
}
