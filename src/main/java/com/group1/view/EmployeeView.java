package com.group1.view;

import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.group1.component.EmployeeDesign;
import com.group1.model.Employee;
import com.group1.repository.ChucVuRepository;
import com.group1.repository.EmployeeRepository;
import com.group1.repository.GroupRepository;
import com.group1.utils.Action;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Notification;

@Component
@Scope("prototype")
public class EmployeeView extends EmployeeDesign {
	
	private Action action;
	
	private Employee emp;
	
	@Autowired
	private EmployeeRepository empRepository;
	
	@Autowired 
	private ChucVuRepository chucvuRepository;
	
	@Autowired 
	private GroupRepository groupRepository;
	
	@Autowired
	private ApplicationContext appContext;
	
	@PostConstruct
	public void init() {
		editBtn.addClickListener(event ->{
			setAction(Action.EDIT);
		});
		
		removeBtn.addClickListener(event ->{
			empRepository.delete(emp);
			backToEmployeeGrid();
			
			Notification.show("Xóa thành công", Notification.Type.WARNING_MESSAGE);
		});
		
		cancelBtn.addClickListener(event ->{
			switch(action) {
			case ADD:
			case VIEW:
				backToEmployeeGrid();
				break;
			case EDIT:
				setAction(Action.VIEW);
				break;
			}
		});
		
		okBtn.addClickListener(event -> {
//			do you want edit this employee?
			editEmployee();
			empRepository.save(emp);
			setAction(Action.VIEW);
			
			Notification.show("Thành công", Notification.Type.WARNING_MESSAGE);
		});
	}
	
	public void setEmployee(Employee emp) {
		this.emp = emp;
		setAction(Action.VIEW);
	}
	
	public void setAction(Action action) {
		this.action = action;
		switch(action) {
		case VIEW:
			setButtonVisible(false);
			setFieldReadOnly(true);
			cancelBtn.setVisible(true);
			editBtn.setVisible(true);
			removeBtn.setVisible(true);
			showEmployee();
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
			empIdTf.setVisible(false);
			emp = new Employee();
			break;
		}
	}
	
	private void showEmployee() {
		empIdTf.setValue(emp.getId()+"");
		nameTf.setValue(Objects.toString(emp.getName(), ""));
		emailTf.setValue(Objects.toString(emp.getEmail(), ""));
		phoneTf.setValue(Objects.toString(emp.getPhone(), ""));
		addressTf.setValue(Objects.toString(emp.getAddress(), ""));
		chucvuIdTf.setValue(emp.getChucVu()!=null ? emp.getChucVu().getId()+"" : "");
		groupIdTf.setValue(emp.getGroup()!=null ? emp.getGroup().getId()+"" : "");
	}
	
	private void setFieldReadOnly(boolean readable) {
		nameTf.setReadOnly(readable);
		emailTf.setReadOnly(readable);
		phoneTf.setReadOnly(readable);
		chucvuIdTf.setReadOnly(readable);
		groupIdTf.setReadOnly(readable);
		addressTf.setReadOnly(readable);
	}
	
	private void setButtonVisible(boolean visible) {
		cancelBtn.setVisible(visible);
		editBtn.setVisible(visible);
		removeBtn.setVisible(visible);
		okBtn.setVisible(visible);
	}
	
	private void editEmployee() {
		emp.setName(nameTf.getValue());
		emp.setEmail(emailTf.getValue());
		emp.setPhone(phoneTf.getValue());
		emp.setAddress(addressTf.getValue());
		emp.setChucVu(chucvuRepository.findOne(Integer.parseInt(chucvuIdTf.getValue())));
		emp.setGroup(groupRepository.findOne(Integer.parseInt(groupIdTf.getValue())));
	}
	
	private void backToEmployeeGrid() {
		AbstractLayout parent = (AbstractLayout) getParent();
		parent.removeAllComponents();
		parent.addComponent(appContext.getBean(EmployeeGridView.class));
	}
}
