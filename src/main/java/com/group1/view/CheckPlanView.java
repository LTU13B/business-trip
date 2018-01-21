package com.group1.view;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.group1.model.Employee;
import com.group1.model.Plan;
import com.group1.utils.Action;
import com.group1.utils.PlanViewMode;
import com.group1.utils.Role;
import com.vaadin.ui.AbstractLayout;

@Component
@Scope("prototype")
public class CheckPlanView extends PlanView {
	private EmpInfoView empInfoView;

	private Employee checkEmp;
	
	private Role role;

	@PostConstruct
	private void init() {
		planButtonLayout.setVisible(false);
		checkButtonLayout.setVisible(true);
		empInfoView = appContext.getBean(EmpInfoView.class);
		addComponent(empInfoView, 0);

		setMode(PlanViewMode.PROPOSE_PLAN);
//		setPlan(planRepository.findOne(9));
//		setAction(Action.VIEW);

//		empInfoView.showEmployee(plan.getEmp());

		checkCancelBtn.addClickListener(event -> {
			backToCheckGrid();
		});

		checkBtn.addClickListener(event -> {
			plan.setCheckDate(new Date());
			switch(role) {
			case LEADER:
				if (plan.getStatus().getId()==1) {
					plan.setStatus(statusRepository.findOne(2));
					planRepository.save(plan);
					break;
				}
				if (plan.getStatus().getId()==3) {
					plan.setStatus(statusRepository.findOne(4));
					planRepository.save(plan);
					break;
				}
				if (plan.getStatus().getId()==5) {
					plan.setStatus(statusRepository.findOne(6));
					planRepository.save(plan);
					break;
				}	
				if (plan.getStatus().getId()==7) {
					plan.setStatus(statusRepository.findOne(8));
					planRepository.save(plan);
					break;
				}	
				break;
			case ACCOUNTANT:
				if (plan.getStatus().getId()==1) {
					plan.setStatus(statusRepository.findOne(3));
					planRepository.save(plan);
					break;
				}
				if (plan.getStatus().getId()==2) {
					plan.setStatus(statusRepository.findOne(4));
					planRepository.save(plan);
					break;
				}
				if (plan.getStatus().getId()==5) {
					plan.setStatus(statusRepository.findOne(7));
					planRepository.save(plan);
					break;
				}	
				if (plan.getStatus().getId()==6) {
					plan.setStatus(statusRepository.findOne(8));
					planRepository.save(plan);
					break;
				}
				break;
			}
			backToCheckGrid();
		});

		unCheckBtn.addClickListener(event -> {
			plan.setCheckDate(new Date());
			plan.setStatus(statusRepository.findOne(9));
			planRepository.save(plan);
			backToCheckGrid();
		});
		
	}

	@Override
	public void setPlan(Plan plan) {
		super.setPlan(plan);
		setAction(Action.VIEW);
		empInfoView.showEmployee(plan.getEmp());
		
		setCheckButtonVisible(false);
		if (role == null)
			return;
		switch (role) {
		case LEADER:
			Set<Integer> leaderStatus = new HashSet<Integer>(Arrays.asList(1, 3, 5, 7));
			if (leaderStatus.contains(plan.getStatus().getId()))
				setCheckButtonVisible(true);
			break;
		case ACCOUNTANT:
			Set<Integer> accountantStatus = new HashSet<Integer>(Arrays.asList(1, 2, 5, 6));
			if (accountantStatus.contains(plan.getStatus().getId()))
				setCheckButtonVisible(true);
			break;
		}
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		empInfoView.setReadOnly(readOnly);
	}

	public void backToCheckGrid() {
		CheckPlanGridView checkPlanGridView = appContext.getBean(CheckPlanGridView.class);
		if (role==Role.LEADER) {
			checkPlanGridView.setRole(Role.LEADER);
			checkPlanGridView.setEmployee(checkEmp);
		}
		else if (role==Role.ACCOUNTANT) {
			checkPlanGridView.setRole(Role.ACCOUNTANT);
			checkPlanGridView.setEmployee(checkEmp);
		}
		
		AbstractLayout parent = (AbstractLayout) getParent();
		parent.removeAllComponents();
		parent.addComponent(checkPlanGridView);
	}

	public void setCheckButtonVisible(boolean visible) {
		checkBtn.setVisible(visible);
		unCheckBtn.setVisible(visible);
	}

	public Employee getCheckEmp() {
		return checkEmp;
	}

	public void setCheckEmp(Employee checkEmp) {
		this.checkEmp = checkEmp;
	}
	
	
}
