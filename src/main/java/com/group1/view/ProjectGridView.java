package com.group1.view;

import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.group1.component.ProjectGridDesign;
import com.group1.model.Project;
import com.group1.model.User;
import com.group1.repository.ProjectRepository;
import com.group1.repository.UserRepository;
import com.group1.utils.Action;
import com.vaadin.ui.AbstractLayout;

@Component
@Scope("prototype")
public class ProjectGridView extends ProjectGridDesign {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ApplicationContext appContext;
	
	@PostConstruct
	public void init() {
		setCaption("Danh sách dự án");
		projectGrid.getColumn("id").setWidth(100.0);
		
		HashSet<Project> users = new HashSet<>(); 
		Iterable<Project> iterable = projectRepository.findAll();
		iterable.forEach(users::add);
		
		projectGrid.setItems(users);
		projectGrid.sort("id");
		
		projectGrid.addSelectionListener(event ->{
			Project selectedProject = event.getAllSelectedItems().iterator().next();
			ProjectView projectView = appContext.getBean(ProjectView.class);
			projectView.setProject(selectedProject);
			
			AbstractLayout parent = (AbstractLayout) getParent(); 
			parent.removeAllComponents();
			parent.addComponent(projectView);
		});
		
		addProjectBtn.addClickListener(event->{
			ProjectView projectView = appContext.getBean(ProjectView.class);
			projectView.setAction(Action.ADD);
			
			AbstractLayout parent = (AbstractLayout) getParent(); 
			parent.removeAllComponents();
			parent.addComponent(projectView);
		});
	}
}
