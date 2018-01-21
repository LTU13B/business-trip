package com.group1.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.group1.component.AdminDashboardDesgin;
import com.group1.component.CreateChucvuDesign;
import com.group1.component.CreateGroupDesign;
import com.group1.component.CreateJobDesign;
import com.group1.component.CreateProjectDesign;
import com.group1.component.CreateUserDesign;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.CssLayout;

@Component
public class AdminDashboardView extends AdminDashboardDesgin {
	
	@Autowired
	private ApplicationContext appContext;
	
	@PostConstruct
	private void init() {
		userBtn.addClickListener(event ->{
			AbstractLayout content = (AbstractLayout) getParent(); 
			content.removeAllComponents();
			content.addComponent(appContext.getBean(UserGridView.class));
		});
		
		empBtn.addClickListener(event->{
			AbstractLayout content = (AbstractLayout) getParent(); 
			content.removeAllComponents();
			content.addComponent(appContext.getBean(EmployeeGridView.class));
		});
		
		groupBtn.addClickListener(event->{
			AbstractLayout content = (AbstractLayout) getParent(); 
			content.removeAllComponents();
			content.addComponent(appContext.getBean(GroupGridView.class));
		});
		
		projectBtn.addClickListener(event->{
			AbstractLayout content = (AbstractLayout) getParent(); 
			content.removeAllComponents();
			content.addComponent(appContext.getBean(ProjectGridView.class));
		});
		
		jobBtn.addClickListener(event->{
			AbstractLayout content = (AbstractLayout) getParent(); 
			content.removeAllComponents();
			content.addComponent(appContext.getBean(ChucVuJobGridView.class));
		});
	}
}
