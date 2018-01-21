package com.group1.view;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import com.group1.component.BillDesign;
import com.group1.component.JobDesign;
import com.group1.model.Bill;
import com.group1.model.ChucVu;
import com.group1.model.Job;
import com.group1.model.JobDetail;
import com.group1.model.LimitCost;
import com.group1.repository.JobRepository;
import com.group1.service.proposePlan.ProposePlanService;
import com.group1.utils.Action;
import com.group1.utils.PlanViewMode;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.VerticalLayout;

@Component
@Scope("prototype")
public class JobView extends JobDesign {
	
	private JobDetail jobDetail;
	
	private ChucVu chucVu;
	
	private Action action;
	
	private PlanViewMode mode;
	
	@Autowired
	private ProposePlanService service;
	
	@Autowired 
	protected ApplicationContext appContext;
	
	@PostConstruct
	protected void init() {
		setAction(Action.EDIT);
		setMode(PlanViewMode.PROPOSE_PLAN);
		
		jobListCbo.setItems(service.selectAllJob());
		jobListCbo.setItemCaptionGenerator(Job::getName);
		jobListCbo.addSelectionListener(event->{
			Job selectedJob = event.getAllSelectedItems().iterator().next();
			
			if (chucVu==null)
				return;
			for (LimitCost limitCost:selectedJob.getLimitCosts()) 
				if (limitCost.getChucVu().equals(chucVu)) {
					limitCostTf.setValue(limitCost.getLimitCost()+"");
					break;
				}
		});
		
		removeJobBtn.addClickListener(event -> {
			((AbstractLayout)getParent()).removeComponent(this);
		});
		
		addBillBtn.addClickListener(event ->{
			billLayout.addComponent(appContext.getBean(BillView.class));
		});
		
		
	}
	
	public void setAction(Action action) {
		this.action = action;
		switch(action) {
		case VIEW:
			setReadOnly(true);
			removeJobBtn.setVisible(false);
			setAllButtonVisible(false);
			break;
		case EDIT:
			break;
		}
	}
	
	public void setMode(PlanViewMode mode) {
		this.mode = mode;
		switch(mode) {
		case PROPOSE_PLAN:
			addBillBtn.setVisible(false);
			billLayout.setVisible(false);
			break;
		case PLAN_PAYMENT:
			addBillBtn.setVisible(true);
			billLayout.setVisible(true);
			break;
		}
	}
	
	protected void setReadOnly(boolean readOnly) {
		jobListCbo.setReadOnly(readOnly);
		limitCostTf.setReadOnly(readOnly);
		quantityTf.setReadOnly(readOnly);
		proposedCostTf.setReadOnly(readOnly);
		startDateDf.setReadOnly(readOnly);
		endDateDf.setReadOnly(readOnly);
		
		Iterator<com.vaadin.ui.Component> iterator = billLayout.iterator();
		while (iterator.hasNext()) {
			((BillView)iterator.next()).setReadOnly(readOnly);
		}
	}
	
	private void setAllButtonVisible(boolean visible) {
		removeJobBtn.setVisible(visible);
		addBillBtn.setVisible(visible);
	}
	
	public JobDetail getJobDetail() {
		if (jobDetail==null)
			jobDetail = new JobDetail();
		jobDetail.setJob(jobListCbo.getValue());
		jobDetail.setQuantity(Integer.parseInt(quantityTf.getValue()));
		jobDetail.setProposedCost(Integer.parseInt(proposedCostTf.getValue()));
		jobDetail.setStartDate(
				Date.from(startDateDf.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		jobDetail.setEndDate(
				Date.from(endDateDf.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		
		Set<Bill> bills = new HashSet<>();
		Iterator<com.vaadin.ui.Component> iterator = billLayout.iterator();
		while (iterator.hasNext()) {
			BillView billView = (BillView)iterator.next();
			Bill bill = billView.getBill();
			bill.setJobDetail(jobDetail);
//			bills.add(billView.getBill());
			bills.add(bill);
		}
		if (!bills.isEmpty())
			jobDetail.setBills(bills);
		return jobDetail;
	}
	
	public void showJobDetail(JobDetail jobDetail) {
		this.jobDetail = jobDetail;
		jobListCbo.setValue(jobDetail.getJob());
//		limitCostTf.setValue(value);
		quantityTf.setValue(jobDetail.getQuantity()+"");
		proposedCostTf.setValue(jobDetail.getProposedCost()+"");
		startDateDf.setValue(jobDetail.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		endDateDf.setValue(jobDetail.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		
//		for (LimitCost limitCost:job.getLimitCosts()) 
//			if (limitCost.getChucVu().equals(chucVu)) {
//				limitCostTf.setValue(limitCost.getLimitCost()+"");
//				break;
//			}		
		
		Set<Bill> bills = jobDetail.getBills();
		if (bills==null)
			return;
		Iterator iterator = bills.iterator();
		billLayout.removeAllComponents();
		while (iterator.hasNext()) {
			Bill bill = (Bill) iterator.next();
			BillView billView = appContext.getBean(BillView.class);
			billView.setAction(action);
			billView.setBill(bill);
			billLayout.addComponent(billView);
		}
	}

	public ChucVu getChucVu() {
		return chucVu;
	}

	public void setChucVu(ChucVu chucVu) {
		this.chucVu = chucVu;
	}
	
	
}
