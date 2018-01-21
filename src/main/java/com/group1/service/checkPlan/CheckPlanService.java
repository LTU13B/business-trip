package com.group1.service.checkPlan;

import java.util.Set;

import com.group1.model.Employee;
import com.group1.model.Plan;

public interface CheckPlanService {

	public Set<Plan> getCheckPlanForLeader(Employee leader);
	
	public Set<Plan> getCheckPlanForAccountant(Employee accountant);
}
