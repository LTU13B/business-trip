package com.group1.view;

import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.group1.component.UserGridDesign;
import com.group1.model.Employee;
import com.group1.model.User;
import com.group1.repository.EmployeeRepository;
import com.group1.repository.UserRepository;
import com.group1.utils.Action;
import com.vaadin.ui.AbstractLayout;

@Component
@Scope("prototype")
public class UserGridView extends UserGridDesign {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ApplicationContext appContext;
	
	@PostConstruct
	public void init() {
		setCaption("Danh sách USER");
		userGrid.getColumn("id").setWidth(100.0);
		
		HashSet<User> users = new HashSet<>(); 
		Iterable<User> iterable = userRepository.findAll();
		iterable.forEach(users::add);
		
		userGrid.setItems(users);
		userGrid.sort("id");
		
		userGrid.addSelectionListener(event ->{
			User selectedUser = event.getAllSelectedItems().iterator().next();
			UserView userView = appContext.getBean(UserView.class);
			userView.setUser(selectedUser);
			
			AbstractLayout parent = (AbstractLayout) getParent(); 
			parent.removeAllComponents();
			parent.addComponent(userView);
		});
		
		addUserBtn.addClickListener(event->{
			UserView userView = appContext.getBean(UserView.class);
			userView.setAction(Action.ADD);
			
			AbstractLayout parent =(AbstractLayout)getParent();
			parent.removeAllComponents();
			parent.addComponent(userView);
		});
	}
}
