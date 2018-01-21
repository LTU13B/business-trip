package com.group1.service.proposePlan;

import java.util.Date;
import java.util.Set;

import com.group1.model.Employee;
import com.group1.model.Job;
import com.group1.model.JobDetail;
import com.group1.model.Plan;
import com.group1.model.User;

public interface ProposePlanService {
	public Plan createPlan(String planName, int projectId, String purpose,
			String place, String partner, boolean advance, boolean arised);
	
	public JobDetail createJobDetail(Job job, int quantity, int proposedCost,
			Date startDate, Date endDate);
	
	public Set<Job> selectAllJob();
	
	public void insertPlan(Plan plan);
	
	public Set<Plan> selectAllPlan(Employee emp);
}
