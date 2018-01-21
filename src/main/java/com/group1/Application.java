/*package com.group1;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.group1.model.Bill;
import com.group1.model.ChucVu;
import com.group1.model.Employee;
import com.group1.model.Job;
import com.group1.model.JobDetail;
import com.group1.model.LimitCost;
import com.group1.model.Plan;
import com.group1.repository.*;
import com.group1.service.checkPlan.CheckPlanService;
import com.group1.service.proposePlan.*;
import com.group1.service.user.UserService;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Autowired
	private LimitCostRepository jobRepository;
	
	@Override
	@Transactional
	public void run(String... strings) throws Exception {
//		jobRepository.delete(9);
	}
}
*/