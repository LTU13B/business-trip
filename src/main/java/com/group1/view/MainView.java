package com.group1.view;

import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.group1.component.AdminDashboardDesgin;
import com.group1.component.CheckPlanDesign;
import com.group1.component.GroupGridDesign;
import com.group1.component.MainDesign;
import com.group1.component.UserGridDesign;
import com.group1.model.Role;
import com.group1.model.User;
import com.vaadin.server.Page;

@Component
@Scope("prototype")
public class MainView extends MainDesign {
	
	@Autowired 
	protected ApplicationContext appContext;
	
	private User user;
	
	@PostConstruct
	private void init() {
		checkBtn.setVisible(false);
		adminBtn.setVisible(false);
		
		proposeBtn.addClickListener(event -> {
			contentLayout.removeAllComponents();
			PlanGridView planGridView = appContext.getBean(PlanGridView.class);
			planGridView.setEmployee(user.getEmp());
			planGridView.setCaption("Kế hoạch đã đề xuất");
			contentLayout.addComponent(planGridView);
		});
		
		checkBtn.addClickListener(event -> {
			contentLayout.removeAllComponents();
			CheckDashboardView checkDashboardView = appContext.getBean(CheckDashboardView.class);
			checkDashboardView.setUser(user);
			contentLayout.addComponent(checkDashboardView);
		});
		
		reportBtn.setVisible(false);
		reportBtn.addClickListener(event -> {
			contentLayout.removeAllComponents();
//			contentLayout.addComponent();
		});
		
		adminBtn.addClickListener(event -> {
			contentLayout.removeAllComponents();
			contentLayout.addComponent(appContext.getBean(AdminDashboardView.class));
		});
		
		userBtn.addClickListener(event->{
			UserDashboardView userDashboardView = appContext.getBean(UserDashboardView.class);
			userDashboardView.setUser(user);
			
			contentLayout.removeAllComponents();
			contentLayout.addComponent(userDashboardView);
		});
		
		logoutBtn.addClickListener(event->{
			Page.getCurrent().reload();
		});
		
		
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		Iterator<Role> iterator = user.getRoles().iterator();
		while(iterator.hasNext()) {
			Role role = iterator.next();
			switch(role.getId()) {
			case 2:
			case 3:
				checkBtn.setVisible(true);
				break;
			case 4:
				adminBtn.setVisible(true);
				break;
			}
		}
		
		empNameLbl.setValue("Xin chào "+ user.getEmp().getName());
	}
	
	
}
