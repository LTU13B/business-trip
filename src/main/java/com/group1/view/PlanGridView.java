package com.group1.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.group1.component.PlanGridDesign;
import com.group1.model.Employee;
import com.group1.model.Plan;
import com.group1.model.User;
import com.group1.service.proposePlan.ProposePlanService;
import com.group1.utils.Action;
import com.group1.utils.PlanViewMode;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.AbstractLayout;

@Component
@Scope("prototype")
public class PlanGridView extends PlanGridDesign {
	@Autowired
	protected ProposePlanService service;

	@Autowired
	protected ApplicationContext appContext;

	private Employee emp;
	
	@PostConstruct
	protected void init() {
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
		
		searchBtn.setVisible(false);
		
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		grid.addColumn(plan -> 
			plan.getCreateDate()!=null ? dateFormat.format(plan.getCreateDate()) : null).setCaption("Ngày tạo");
		grid.addColumn(plan -> 
			plan.getCheckDate()!=null ? dateFormat.format(plan.getCheckDate()) : null).setCaption("Ngày duyệt");
		grid.addColumn(plan -> plan.getStatus().getName()).setCaption("Trạng thái");
		grid.sort("createDate",SortDirection.DESCENDING);
		
		if (emp!=null)
			grid.setItems(service.selectAllPlan(emp));

		addBtn.addClickListener(event -> {
			PlanView planView = appContext.getBean(PlanView.class);
			planView.setEmployee(emp);
			planView.setAction(Action.ADD);
			
			AbstractLayout parent = ((AbstractLayout) getParent());
			parent.removeAllComponents();
			parent.addComponent(planView);
		});
		
		grid.addSelectionListener(event ->{
			Plan plan = event.getFirstSelectedItem().get();
			PlanView planView = appContext.getBean(PlanView.class);
			planView.setEmployee(emp);
			planView.setPlan(plan);
			planView.setAction(Action.VIEW);
			
			AbstractLayout parent = (AbstractLayout) getParent();
			parent.removeAllComponents();
			parent.addComponent(planView);
		});
	}


	public void setEmployee(Employee emp) {
		this.emp = emp;
		grid.setItems(service.selectAllPlan(emp));
	}
	
	
}
