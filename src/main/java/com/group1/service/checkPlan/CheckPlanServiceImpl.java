package com.group1.service.checkPlan;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group1.model.Employee;
import com.group1.model.Group;
import com.group1.model.Plan;
import com.group1.repository.EmployeeRepository;
import com.group1.repository.GroupRepository;
import com.group1.repository.PlanRepository;
import com.group1.service.user.UserService;

@Service
public class CheckPlanServiceImpl implements CheckPlanService {
	
	@Autowired
	private EmployeeRepository empRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private UserService userService;
	
	@Override
	@Transactional
	public Set<Plan> getCheckPlanForLeader(Employee leader) {
		Set<Group> groups = groupRepository.findByLeader(leader);
		Iterator iterator = groups.iterator();
		
		HashSet<Employee> emps = new HashSet<>();
		while(iterator.hasNext()) { 
			Group group = (Group) iterator.next();
			emps.addAll(empRepository.findByGroup(group));
		}
		emps.remove(leader);
		
		HashSet<Plan> plans = new HashSet<>();
		iterator = emps.iterator();
		while (iterator.hasNext()) {
			Employee emp = (Employee) iterator.next();
			Set<Plan> tmp = planRepository.findByEmp(emp);
//			plans.addAll(planRepository.findByEmp(emp));
			plans.addAll(tmp);
		}
		return plans;
	}

	@Override
	public Set<Plan> getCheckPlanForAccountant(Employee accountant) {
		Set<Group> groups = groupRepository.findByAccountant(accountant);
		Iterator iterator = groups.iterator();
		
		HashSet<Employee> emps = new HashSet<>();
		while(iterator.hasNext()) { 
			Group group = (Group) iterator.next();
			emps.addAll(empRepository.findByGroup(group));
		}
		emps.remove(accountant);
		
		HashSet<Plan> plans = new HashSet<>();
		iterator = emps.iterator();
		while (iterator.hasNext()) {
			Employee emp = (Employee) iterator.next();
			Set<Plan> tmp = planRepository.findByEmp(emp);
//			plans.addAll(planRepository.findByEmp(emp));
			plans.addAll(tmp);
		}
		return plans;
	}

}
