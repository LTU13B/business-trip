package com.group1.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.group1.component.PlanGridDesign;
import com.group1.model.Employee;
import com.group1.model.Plan;
import com.group1.model.User;
import com.group1.service.checkPlan.CheckPlanService;
import com.group1.utils.Role;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Grid.SelectionMode;

@Component
@Scope("prototype")
public class CheckPlanGridView extends PlanGridDesign {
	
	@Autowired
	private CheckPlanService checkPlanService;
	
	@Autowired
	private ApplicationContext appContext;
	
	private Employee emp;
	
	private Role role;
	
	@PostConstruct
	protected void init() {
		
		buttonLayout.setVisible(false);
		
		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.removeColumn("id");
		grid.removeColumn("emp");
		grid.removeColumn("project");
		grid.removeColumn("purpose");
		grid.removeColumn("place");
		grid.removeColumn("partner");
		grid.removeColumn("advance");
		grid.removeColumn("advanceCost");
		grid.removeColumn("arised");
		grid.removeColumn("jobDetails");
//		grid.removeColumn("createDate");
		grid.removeColumn("checkDate");
		grid.removeColumn("status");

		grid.getColumn("createDate").setHidden(true);
		grid.sort("createDate",SortDirection.DESCENDING);
		
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		grid.addColumn(plan -> 
			plan.getCreateDate()!=null ? dateFormat.format(plan.getCreateDate()) : null).setCaption("Ngày tạo");
		grid.addColumn(plan -> 
			plan.getCheckDate()!=null ? dateFormat.format(plan.getCheckDate()) : null).setCaption("Ngày duyệt");
		grid.addColumn(plan -> plan.getStatus().getName()).setCaption("Trạng thái");
		
		grid.addSelectionListener(event ->{
			Plan plan = event.getFirstSelectedItem().get();
			CheckPlanView checkPlanView = appContext.getBean(CheckPlanView.class);
			checkPlanView.setRole(role);
			checkPlanView.setEmployee(plan.getEmp());
			checkPlanView.setPlan(plan);
			checkPlanView.setCheckEmp(emp);
			
			AbstractLayout parent = (AbstractLayout) getParent();
			parent.removeAllComponents();
			parent.addComponent(checkPlanView);
		});
	}

	public void setEmployee(Employee emp) {
		this.emp = emp;
		if (role==null)
			return;
		if (role==Role.LEADER)
			grid.setItems(checkPlanService.getCheckPlanForLeader(emp));
		else if (role==Role.ACCOUNTANT)
			grid.setItems(checkPlanService.getCheckPlanForAccountant(emp));
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	
}
