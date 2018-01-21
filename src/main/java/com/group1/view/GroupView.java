package com.group1.view;

import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.group1.component.GroupDesign;
import com.group1.model.Group;
import com.group1.model.User;
import com.group1.repository.EmployeeRepository;
import com.group1.repository.GroupRepository;
import com.group1.utils.Action;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Notification;

@Component
@Scope("prototype")
public class GroupView extends GroupDesign {
	
	private Group group;
	
	private Action action;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private EmployeeRepository empRepository;
	
	@Autowired
	private ApplicationContext appContext;
	
	@PostConstruct
	private void init() {
		editBtn.addClickListener(event ->{
			setAction(Action.EDIT);
		});
		
		removeBtn.addClickListener(event ->{
			groupRepository.delete(group);
			backToGroupGrid();
			
			Notification.show("Xóa thành công", Notification.Type.WARNING_MESSAGE);
		});
		
		cancelBtn.addClickListener(event ->{
			switch(action) {
			case ADD:
			case VIEW:
				backToGroupGrid();
				break;
			case EDIT:
				setAction(Action.VIEW);
				break;
			}
		});
		
		okBtn.addClickListener(event -> {
//			do you want edit this employee?
			editGroup();
			groupRepository.save(group);
			setAction(Action.VIEW);
			
			Notification.show("Thay đổi thành công", Notification.Type.WARNING_MESSAGE);
		});
	}
	
	public void setGroup(Group group) {
		this.group = group;
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
			showGroup();
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
			groupIdTf.setVisible(false);
			group = new Group();
			break;
		}
	}
	
	private void showGroup() {
		groupIdTf.setValue(Objects.toString(group.getId(), ""));
		groupNameTf.setValue(Objects.toString(group.getName(), ""));
		leaderIdTf.setValue(Objects.toString(group.getLeader().getId(), ""));
		accountantIdTf.setValue(Objects.toString(group.getAccountant().getId(), ""));
	}
	
	private void setFieldReadOnly(boolean readOnly) {
		groupNameTf.setReadOnly(readOnly);
		leaderIdTf.setReadOnly(readOnly);
		accountantIdTf.setReadOnly(readOnly);
	}
	
	private void setButtonVisible(boolean visible) {
		cancelBtn.setVisible(visible);
		editBtn.setVisible(visible);
		removeBtn.setVisible(visible);
		okBtn.setVisible(visible);
	}
	
	private void editGroup() {
		group.setName(Objects.toString(groupNameTf.getValue(), ""));
		group.setLeader(empRepository.findOne(Integer.parseInt(leaderIdTf.getValue())));
		group.setAccountant(empRepository.findOne(Integer.parseInt(accountantIdTf.getValue())));
	}
	
	private void backToGroupGrid() {
		AbstractLayout parent = (AbstractLayout) getParent();
		parent.removeAllComponents();
		parent.addComponent(appContext.getBean(GroupGridView.class));
	}
}
