package com.group1.repository;

import org.springframework.data.repository.CrudRepository;

import com.group1.model.Bill;

public interface BillRepository extends CrudRepository<Bill, Integer> {

}
