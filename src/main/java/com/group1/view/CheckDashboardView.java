package com.group1.view;

import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.group1.component.CheckDashboardDesign;
import com.group1.model.User;
import com.group1.utils.Role;
import com.vaadin.ui.AbstractLayout;

@Component
@Scope("prototype")
public class CheckDashboardView extends CheckDashboardDesign {

	@Autowired
	private ApplicationContext appContext;
	
	private User user;
	
	@PostConstruct
	private void init() {
		leaderBtn.setVisible(false);
		accountantBtn.setVisible(false);
		
		leaderBtn.addClickListener(event->{
			CheckPlanGridView checkPlanGridView = appContext.getBean(CheckPlanGridView.class);
			checkPlanGridView.setRole(Role.LEADER);
			checkPlanGridView.setEmployee(user.getEmp());
			checkPlanGridView.setCaption("Duyệt với vai trò lãnh đạo");
			
			AbstractLayout parent = (AbstractLayout) getParent();
			parent.removeAllComponents();
			parent.addComponent(checkPlanGridView);
		});
		
		accountantBtn.addClickListener(event->{
			CheckPlanGridView checkPlanGridView = appContext.getBean(CheckPlanGridView.class);
			checkPlanGridView.setRole(Role.ACCOUNTANT);
			checkPlanGridView.setEmployee(user.getEmp());
			checkPlanGridView.setCaption("Duyệt với vai trò kế toán");
			
			AbstractLayout parent = (AbstractLayout) getParent();
			parent.removeAllComponents();
			parent.addComponent(checkPlanGridView);
		});
	}
	
	public void setUser(User user) {
		this.user = user;
		Iterator<com.group1.model.Role> iterator = user.getRoles().iterator();
		while(iterator.hasNext()) {
			com.group1.model.Role role = iterator.next();
			switch(role.getId()) {
			case 2:
				leaderBtn.setVisible(true);
				break;
			case 3:
				accountantBtn.setVisible(true);
			}
		}
	}
}
