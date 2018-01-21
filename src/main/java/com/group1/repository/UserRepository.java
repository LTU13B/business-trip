package com.group1.repository;

import org.springframework.data.repository.CrudRepository;

import com.group1.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	User findByUserName(String userName);
}
