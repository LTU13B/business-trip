package com.group1.view;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.group1.component.PlanDesign;
import com.group1.model.Employee;
import com.group1.model.JobDetail;
import com.group1.model.Plan;
import com.group1.repository.JobDetailRepository;
import com.group1.repository.PlanRepository;
import com.group1.repository.PlanStatusRepository;
import com.group1.repository.ProjectRepository;
import com.group1.service.proposePlan.ProposePlanService;
import com.group1.service.user.UserService;
import com.group1.utils.Action;
import com.group1.utils.PlanViewMode;
import com.group1.utils.WriteExcel;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.AbstractLayout;

@Component
@Scope("prototype")
@Primary
public class PlanView extends PlanDesign {

	protected Plan plan;

	private Action action;

	private PlanViewMode mode;

	private Employee emp;

	@Autowired
	private ProposePlanService service;

	@Autowired
	protected PlanRepository planRepository;

	@Autowired
	private JobDetailRepository jobRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	protected ApplicationContext appContext;

	@Autowired
	private UserService userService;

	@Autowired
	protected PlanStatusRepository statusRepository;

	@PostConstruct
	private void init() {
		checkButtonLayout.setVisible(false);

		mode = PlanViewMode.PROPOSE_PLAN;

		cancelBtn.addClickListener(event -> {
			switch (action) {
			case ADD:
			case VIEW:
				backToPlanGrid();
				break;
			case EDIT:
				setAction(Action.VIEW);
				break;
			}
		});

		editBtn.addClickListener(event -> {
			switch (action) {
			case VIEW:
				setAction(Action.EDIT);
				break;
			case EDIT:
				editPlan();
				if (mode == PlanViewMode.PLAN_PAYMENT)
					plan.setStatus(statusRepository.findOne(5));
				planRepository.save(plan);
				setAction(Action.VIEW);
				break;
			}
		});

		deleteBtn.addClickListener(event -> {
			Iterator<JobDetail> iterator = plan.getJobDetails().iterator();
			while (iterator.hasNext()) {
				jobRepository.delete(iterator.next());
			}
			planRepository.delete(plan);
			backToPlanGrid();
		});

		proposeBtn.addClickListener(event -> {
			editPlan();
			plan.setCreateDate(new Date());
			planRepository.save(plan);
			setAction(Action.VIEW);
		});

		addJobBtn.addClickListener(event -> {
			JobView jobView = appContext.getBean(JobView.class);
			jobView.setChucVu(emp.getChucVu());
			jobView.setMode(mode);
			jobListLayout.addComponent(jobView);
		});

		proposePaymentBtn.addClickListener(event -> {
			PlanView planView = appContext.getBean(PlanView.class);
			planView.setPlan(plan);
			planView.setMode(PlanViewMode.PLAN_PAYMENT);
			planView.setEmployee(emp);
			planView.setAction(Action.EDIT);

			AbstractLayout parent = (AbstractLayout) getParent();
			parent.removeAllComponents();
			parent.addComponent(planView);
		});
		
		
	}

	public void setPlan(Plan plan) {
		this.plan = plan;

		WriteExcel writeExcel = appContext.getBean(WriteExcel.class);
		try {
			Resource res = new FileResource(new File(writeExcel.writePlan(plan, "report\\plan")));
			FileDownloader fileDownloader = new FileDownloader(res);
			fileDownloader.extend(reportBtn);
			reportBtn.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setAction(Action action) {
		this.action = action;
		switch (action) {
		case VIEW:
			setReadOnly(true);
			setAllButtonVisible(false);
			cancelBtn.setVisible(true);
			if (plan.getStatus().getId() == 1) {
				editBtn.setVisible(true);
				deleteBtn.setVisible(true);
			}
			if (plan.getStatus().getId() == 4) {
				proposePaymentBtn.setVisible(true);
			}
			if (plan.getStatus().getId() >= 4) {
				setMode(PlanViewMode.PLAN_PAYMENT);
			}
			if (plan.getStatus().getId() == 5) {
				editBtn.setVisible(true);
			}
			showPlan();
			break;
		case EDIT:
			if (mode == PlanViewMode.PROPOSE_PLAN)
				setReadOnly(false);
			else
				setReadOnly(true);
			setAllButtonVisible(false);
			cancelBtn.setVisible(true);
			editBtn.setVisible(true);
			addJobBtn.setVisible(true);
			showPlan();
			break;
		case ADD:
			setReadOnly(false);
			setAllButtonVisible(false);
			cancelBtn.setVisible(true);
			proposeBtn.setVisible(true);
			addJobBtn.setVisible(true);
			plan = new Plan();
			plan.setEmp(emp);
			plan.setStatus(statusRepository.findOne(1));
			break;
		}
	}

	public void setMode(PlanViewMode mode) {
		this.mode = mode;
		switch (mode) {
		case PROPOSE_PLAN:
			setReadOnly(false);
			break;
		case PLAN_PAYMENT:
			setReadOnly(true);
			break;
		}
	}

	private void showPlan() {
		if (plan == null)
			return;
		planNameTf.setValue(Objects.toString(plan.getName(), ""));
		projectIdTf.setValue(Objects.toString(plan.getProject().getId(), ""));
		planPurposeTf.setValue(Objects.toString(plan.getPurpose(), ""));
		planPlaceTf.setValue(Objects.toString(plan.getPlace(), ""));
		planPartnerTf.setValue(Objects.toString(plan.getPartner(), ""));
		planAdvanceChbo.setValue(plan.isAdvance());
		planArisedChbox.setValue(plan.isArised());

		Set<JobDetail> jobs = plan.getJobDetails();
		Iterator<JobDetail> iterator = jobs.iterator();
		jobListLayout.removeAllComponents();
		while (iterator.hasNext()) {
			JobView jobView = appContext.getBean(JobView.class);
			jobView.setChucVu(emp.getChucVu());
			jobView.setMode(mode);
			jobView.setAction(action);
			jobView.showJobDetail(iterator.next());
			jobListLayout.addComponent(jobView);
		}
	}

	public void setReadOnly(boolean readOnly) {
		planNameTf.setReadOnly(readOnly);
		projectIdTf.setReadOnly(readOnly);
		planPurposeTf.setReadOnly(readOnly);
		planPlaceTf.setReadOnly(readOnly);
		planPartnerTf.setReadOnly(readOnly);
		planAdvanceChbo.setReadOnly(readOnly);
		planArisedChbox.setReadOnly(readOnly);
	}

	private void setAllButtonVisible(boolean visible) {
		cancelBtn.setVisible(visible);
		proposeBtn.setVisible(visible);
		editBtn.setVisible(visible);
		deleteBtn.setVisible(visible);
		proposePaymentBtn.setVisible(visible);
		addJobBtn.setVisible(visible);
	}

	private void editPlan() {
		plan.setName(planNameTf.getValue());
		plan.setProject(
				projectIdTf.getValue() != null ? projectRepository.findOne(Integer.parseInt(projectIdTf.getValue()))
						: null);
		plan.setPurpose(planPurposeTf.getValue());
		plan.setPlace(planPlaceTf.getValue());
		plan.setPartner(planPartnerTf.getValue());
		plan.setAdvance(planAdvanceChbo.getValue());
		plan.setArised(planArisedChbox.getValue());
		
		if (plan.getJobDetails()!=null)
			plan.getJobDetails().clear();
		else
			plan.setJobDetails(new HashSet<JobDetail>());
		plan.getJobDetails().addAll(getJobDetails());
//		plan.setJobDetails(getJobDetails());
	}

	private Set<JobDetail> getJobDetails() {
		Set<JobDetail> jobs = new HashSet<>();
		Iterator<com.vaadin.ui.Component> iterator = jobListLayout.iterator();
		while (iterator.hasNext()) {
			JobView jobView = (JobView) iterator.next();
			JobDetail jobDetail = jobView.getJobDetail();
			jobDetail.setPlan(plan);
			jobs.add(jobDetail);
		}
		return jobs;
	}

	private void backToPlanGrid() {
		AbstractLayout parent = (AbstractLayout) getParent();
		parent.removeAllComponents();
		PlanGridView planGridView = appContext.getBean(PlanGridView.class);
		planGridView.setEmployee(emp);
		parent.addComponent(planGridView);
	}

	public Employee getEmployee() {
		return emp;
	}

	public void setEmployee(Employee emp) {
		this.emp = emp;
	}

}
