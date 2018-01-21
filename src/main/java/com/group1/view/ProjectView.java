package com.group1.view;

import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.group1.component.ProjectDesign;
import com.group1.model.Project;
import com.group1.model.User;
import com.group1.repository.ProjectRepository;
import com.group1.repository.UserRepository;
import com.group1.utils.Action;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Notification;

@Component
@Scope("prototype")
public class ProjectView extends ProjectDesign {

	private Project project;
	
	private Action action;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ApplicationContext appContext;
	
	@PostConstruct
	private void init() {
		editBtn.addClickListener(event ->{
			setAction(Action.EDIT);
		});
		
		removeBtn.addClickListener(event ->{
			projectRepository.delete(project);
			backToProjectGrid();
			
			Notification.show("Xóa thành công", Notification.Type.WARNING_MESSAGE);
		});
		
		cancelBtn.addClickListener(event ->{
			switch(action) {
			case ADD:
			case VIEW:
				backToProjectGrid();
				break;
			case EDIT:
				setAction(Action.VIEW);
				break;
			}
		});
		
		okBtn.addClickListener(event -> {
//			do you want edit this employee?
			editProject();
			projectRepository.save(project);
			setAction(Action.VIEW);
			
			Notification.show("Thay đổi thành công", Notification.Type.WARNING_MESSAGE);
		});
	}
	
	public void setProject(Project project) {
		this.project = project;
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
			showProject();
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
			projectIdTf.setVisible(false);
			project = new Project();
			break;
		}
	}
	
	private void showProject() {
		projectIdTf.setValue(Objects.toString(project.getId(), ""));
		projectNameTf.setValue(Objects.toString(project.getName(), ""));
		usageCostTf.setValue(Objects.toString(project.getUsageCost(), ""));
		limitCostTf.setValue(Objects.toString(project.getLimitCost(), ""));
	}
	
	private void setFieldReadOnly(boolean readOnly) {
		projectIdTf.setReadOnly(readOnly);
		projectNameTf.setReadOnly(readOnly);
		usageCostTf.setReadOnly(readOnly);
		limitCostTf.setReadOnly(readOnly);
	}
	
	private void setButtonVisible(boolean visible) {
		cancelBtn.setVisible(visible);
		editBtn.setVisible(visible);
		removeBtn.setVisible(visible);
		okBtn.setVisible(visible);
	}
	
	private void editProject() {
		project.setName(Objects.toString(projectNameTf.getValue(), ""));
		project.setUsageCost(Long.parseLong(usageCostTf.getValue()));
		project.setLimitCost(Long.parseLong(limitCostTf.getValue()));
	}
	
	private void backToProjectGrid() {
		AbstractLayout parent = (AbstractLayout) getParent();
		parent.removeAllComponents();
		parent.addComponent(appContext.getBean(ProjectGridView.class));
	}
}
