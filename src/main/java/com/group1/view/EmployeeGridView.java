package com.group1.view;

import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.group1.component.EmployeeGridDesign;
import com.group1.model.Employee;
import com.group1.repository.EmployeeRepository;
import com.group1.utils.Action;
import com.vaadin.ui.AbstractLayout;

@Component
@Scope("prototype")
public class EmployeeGridView extends EmployeeGridDesign {

	@Autowired
	private EmployeeRepository empRepository;
	
	@Autowired
	private ApplicationContext appContext;
	
	@PostConstruct
	public void init() {
		setCaption("Danh sách nhân viên");
		empGrid.getColumn("id").setWidth(60.0);
		
		HashSet<Employee> emps = new HashSet<>(); 
		Iterable<Employee> iterable = empRepository.findAll();
		iterable.forEach(emps::add);
		
		empGrid.setItems(emps);
		empGrid.addSelectionListener(event ->{
			Employee selectedEmp = event.getAllSelectedItems().iterator().next();
			AbstractLayout parent = (AbstractLayout) getParent(); 
			parent.removeAllComponents();
			EmployeeView employeeView = appContext.getBean(EmployeeView.class);
			employeeView.setEmployee(selectedEmp);
			parent.addComponent(employeeView);
		});
		
		addEmpBtn.addClickListener(event->{
			AbstractLayout parent =(AbstractLayout)getParent();
			parent.removeAllComponents();
			EmployeeView employeeView = appContext.getBean(EmployeeView.class);
			employeeView.setAction(Action.ADD);
			parent.addComponent(employeeView);
		});
		
	}

}
