package com.group1.view;

import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.group1.component.GroupGridDesign;
import com.group1.model.Group;
import com.group1.model.User;
import com.group1.repository.GroupRepository;
import com.group1.repository.UserRepository;
import com.group1.utils.Action;
import com.vaadin.ui.AbstractLayout;

@Component
@Scope("prototype")
public class GroupGridView extends GroupGridDesign {

	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private ApplicationContext appContext;
	
	@PostConstruct
	public void init() {
		setCaption("Danh sách phòng ban");
		userGrid.getColumn("id").setWidth(100.0);
		
		HashSet<Group> groups = new HashSet<>(); 
		Iterable<Group> iterable = groupRepository.findAll();
		iterable.forEach(groups::add);
		
		userGrid.setItems(groups);
		userGrid.sort("id");
		
		userGrid.addSelectionListener(event ->{
			Group selectedGroup = event.getAllSelectedItems().iterator().next();
			GroupView groupView = appContext.getBean(GroupView.class);
			groupView.setGroup(selectedGroup);
			
			AbstractLayout parent = (AbstractLayout) getParent(); 
			parent.removeAllComponents();
			parent.addComponent(groupView);
		});
		
		addGroupBtn.addClickListener(event->{
			GroupView groupView = appContext.getBean(GroupView.class);
			groupView.setAction(Action.ADD);
			
			AbstractLayout parent = (AbstractLayout) getParent(); 
			parent.removeAllComponents();
			parent.addComponent(groupView);
		});
	}
}
