package com.group1.view;

import java.util.HashSet;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.group1.component.UserDesign;
import com.group1.model.Employee;
import com.group1.model.Role;
import com.group1.model.User;
import com.group1.repository.EmployeeRepository;
import com.group1.repository.RoleRepository;
import com.group1.repository.UserRepository;
import com.group1.utils.Action;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Notification;

@Component
@Scope("prototype")
public class UserView extends UserDesign {

	private User user;

	private Action action;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmployeeRepository empRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ApplicationContext appContext;
	
	@PostConstruct
	private void init() {
		editBtn.addClickListener(event ->{
			setAction(Action.EDIT);
		});
		
		removeBtn.addClickListener(event ->{
			userRepository.delete(user);
			backToUserGrid();
			
			Notification.show("Xóa thành công", Notification.Type.WARNING_MESSAGE);
		});
		
		cancelBtn.addClickListener(event ->{
			switch(action) {
			case ADD:
			case VIEW:
				backToUserGrid();
				break;
			case EDIT:
				setAction(Action.VIEW);
				break;
			}
		});
		
		okBtn.addClickListener(event -> {
//			do you want edit this employee?
			editUser();
			userRepository.save(user);
			setAction(Action.VIEW);
			
			Notification.show("Thay đổi thành công", Notification.Type.WARNING_MESSAGE);
		});
	}

	public void setUser(User user) {
		this.user = user;
		setAction(Action.VIEW);
	}

	public void setAction(Action action) {
		this.action = action;
		switch (action) {
		case VIEW:
			setButtonVisible(false);
			setFieldReadOnly(true);
			cancelBtn.setVisible(true);
			editBtn.setVisible(true);
			removeBtn.setVisible(true);
			showUser();
			break;
		case EDIT:
			setButtonVisible(false);
			setFieldReadOnly(false);
			cancelBtn.setVisible(true);
			okBtn.setVisible(true);
			break;
		case ADD:
			setButtonVisible(false);
			setFieldReadOnly(false);
			cancelBtn.setVisible(true);
			okBtn.setVisible(true);
			userIdTf.setVisible(false);
			user = new User();
			break;
		}
	}

	private void showUser() {
		userIdTf.setValue(Objects.toString(user.getId(), ""));
		empIdTf.setValue(Objects.toString(user.getEmp().getId(), ""));
		usernameTf.setValue(Objects.toString(user.getUserName(), ""));
		passTf.setValue(Objects.toString(user.getPassword(), ""));
		
		roleChboxGroup.deselectAll();
		for(Role role:user.getRoles())
			roleChboxGroup.select(role.getName());
	}
	
	private void setFieldReadOnly(boolean readOnly) {
		empIdTf.setReadOnly(readOnly);
		usernameTf.setReadOnly(readOnly);
		passTf.setReadOnly(readOnly);
		roleChboxGroup.setReadOnly(readOnly);
	}
	
	private void setButtonVisible(boolean visible) {
		cancelBtn.setVisible(visible);
		editBtn.setVisible(visible);
		removeBtn.setVisible(visible);
		okBtn.setVisible(visible);
	}
	
	
	private void editUser() {
		user.setEmp(empRepository.findOne(Integer.parseInt(empIdTf.getValue())));
		user.setUserName(Objects.toString(usernameTf.getValue(),""));
		user.setPassword(Objects.toString(passTf.getValue(), ""));
		
		user.setRoles(new HashSet<Role>());
		for (String roleName:roleChboxGroup.getValue()) {
			if (roleName.equals("LEADER"))
				user.getRoles().add(roleRepository.findOne(2));
			else if (roleName.equals("ACCOUNTANT"))
				user.getRoles().add(roleRepository.findOne(3));
			else if (roleName.equals("ADMIN"))
				user.getRoles().add(roleRepository.findOne(4));
		}
	}
	
	private void backToUserGrid() {
		AbstractLayout parent = (AbstractLayout) getParent();
		parent.removeAllComponents();
		parent.addComponent(appContext.getBean(UserGridView.class));
	}
}
