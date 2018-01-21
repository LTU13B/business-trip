package com.group1.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.group1.component.UserDashboardDesign;
import com.group1.model.User;
import com.vaadin.ui.AbstractLayout;

@Component
@Scope("prototype")
public class UserDashboardView extends UserDashboardDesign {

	private User user;
	
	@Autowired
	private ApplicationContext appContext;
	
	@PostConstruct
	private void init() {
		changePassBtn.addClickListener(event->{
			AbstractLayout parent = (AbstractLayout)getParent();
			parent.removeAllComponents();
			
			ChangePasswordView changePasswordView = appContext.getBean(ChangePasswordView.class);
			changePasswordView.setUser(user);
			parent.addComponent(changePasswordView);
		});
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
