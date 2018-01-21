package com.group1.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group1.model.Employee;
import com.group1.model.User;
import com.group1.repository.EmployeeRepository;
import com.group1.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private EmployeeRepository empRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Employee getCurrentEmployee() {
		return empRepository.findOne(3);
	}

	@Override
	public Employee getCurrentLeader() {
		return empRepository.findOne(1);
	}

	@Override
	public boolean authentication(String userName, String password) {
		User user = userRepository.findByUserName(userName);
		if (user==null || !user.getPassword().equals(password))
			return false;
		return true;
	}

	@Override
	public User getUser(String userName) {
		return userRepository.findByUserName(userName);
	}

}
