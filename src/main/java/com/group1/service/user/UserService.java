package com.group1.service.user;

import com.group1.model.Employee;
import com.group1.model.User;

public interface UserService {
	public Employee getCurrentEmployee();
	
	public Employee getCurrentLeader();
	
	public boolean authentication(String userName, String password);
	
	public User getUser(String userName);
}
