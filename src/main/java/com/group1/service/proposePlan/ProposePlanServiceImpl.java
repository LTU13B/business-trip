package com.group1.service.proposePlan;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group1.model.Employee;
import com.group1.model.Job;
import com.group1.model.JobDetail;
import com.group1.model.Plan;
import com.group1.model.User;
import com.group1.repository.JobRepository;
import com.group1.repository.PlanRepository;
import com.group1.repository.PlanStatusRepository;
import com.group1.repository.ProjectRepository;
import com.group1.service.user.UserService;

@Service
public class ProposePlanServiceImpl implements ProposePlanService {
	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private PlanStatusRepository statusRepository;
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private UserService userService;

	@Override
	public Plan createPlan(String planName, int projectId, String purpose, String place, String partner,
			boolean advance, boolean arised) {
		Plan plan = new Plan();
		plan.setName(planName);
		plan.setEmp(userService.getCurrentEmployee());
		plan.setCreateDate(new Date());
		plan.setStatus(statusRepository.findOne(1));
		plan.setProject(projectRepository.findOne(projectId));
		plan.setPurpose(purpose);
		plan.setPlace(place);
		plan.setPartner(partner);
		plan.setAdvance(advance);
		plan.setArised(arised);
		return plan;
	}

	@Override
	public JobDetail createJobDetail(Job job, int quantity, int proposedCost, Date startDate, Date endDate) {
		JobDetail jobDetail = new JobDetail();
		jobDetail.setJob(job);
		jobDetail.setQuantity(quantity);
		jobDetail.setProposedCost(proposedCost);
		jobDetail.setStartDate(startDate);
		jobDetail.setEndDate(endDate);
		return jobDetail;
	}

	@Override
	public void insertPlan(Plan plan) {
		planRepository.save(plan);
	}

	@Override
	public Set<Job> selectAllJob() {
		HashSet hashSet = new HashSet<>();
		Iterator<Job> iterator = jobRepository.findAll().iterator();
		iterator.forEachRemaining(hashSet::add);
		return hashSet;
	}

	@Override
	public Set<Plan> selectAllPlan(Employee emp) {
		return planRepository.findByEmp(emp);
	}
	
	
}
