package com.group1.view;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.group1.component.ChucvuJobDesign;
import com.group1.model.Job;
import com.group1.model.LimitCost;
import com.group1.model.Project;
import com.group1.repository.JobRepository;
import com.group1.repository.LimitCostRepository;
import com.group1.utils.Action;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Notification;

@Component
@Scope("prototype")
public class ChucVuJobView extends ChucvuJobDesign {

	private Job job;

	private Action action;
	
//	private Set<LimitCost> removeList;
	
	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private LimitCostRepository limitRepository;
	
	@Autowired
	private ApplicationContext appContext;

	@PostConstruct
	private void init() {
		editBtn.addClickListener(event -> {
			setAction(Action.EDIT);
		});

		removeBtn.addClickListener(event -> {
			jobRepository.delete(job);
			backToGrid();

			Notification.show("Xóa thành công", Notification.Type.WARNING_MESSAGE);
		});

		cancelBtn.addClickListener(event -> {
			switch (action) {
			case ADD:
			case VIEW:
				backToGrid();
				break;
			case EDIT:
				setAction(Action.VIEW);
				break;
			}
		});

		okBtn.addClickListener(event -> {
			edit();
			jobRepository.save(job);
//			for (LimitCost limit:job.getLimitCosts())
//				limitRepository.save(limit);
//			if (removeList!=null && !removeList.isEmpty())
//				for (LimitCost limit:removeList)
//					limitRepository.delete(limit.getId());
			setAction(Action.VIEW);

			Notification.show("Thay đổi thành công", Notification.Type.WARNING_MESSAGE);
		});

		addLimitCostBtn.addClickListener(event -> {
			LimitCostView limitCostView = appContext.getBean(LimitCostView.class);
			limitCostView.setJob(job);
			limitCostView.setAction(Action.EDIT);

			limitCostLayout.addComponent(limitCostView);
		});
	}

	public void setJob(Job job) {
		this.job = job;
		setAction(Action.VIEW);
	}

	public void setAction(Action action) {
		this.action = action;
		switch (action) {
		case VIEW:
			setButtonVisible(false);
			setFieldReadOnly(true);
			cancelBtn.setVisible(true);
			editBtn.setVisible(true);
			removeBtn.setVisible(true);
			show();
			break;
		case EDIT:
			setButtonVisible(false);
			setFieldReadOnly(false);
			cancelBtn.setVisible(true);
			okBtn.setVisible(true);
			addLimitCostBtn.setVisible(true);
			break;
		case ADD:
			setButtonVisible(false);
			setFieldReadOnly(false);
			cancelBtn.setVisible(true);
			okBtn.setVisible(true);
			jobIdTf.setVisible(false);
			job = new Job();
			break;
		}
	}

	private void show() {
		jobIdTf.setValue(Objects.toString(job.getId(), ""));
		jobNameTf.setValue(Objects.toString(job.getName(), ""));
		limitCostLayout.removeAllComponents();

		Set<LimitCost> limitCosts = job.getLimitCosts();
		for (LimitCost limitCost : limitCosts) {
			LimitCostView limitCostView = appContext.getBean(LimitCostView.class);
			limitCostView.show(limitCost);
			limitCostView.setAction(Action.VIEW);

			limitCostLayout.addComponent(limitCostView);
		}

	}

	private void setFieldReadOnly(boolean readOnly) {
		jobIdTf.setReadOnly(readOnly);
		jobNameTf.setReadOnly(readOnly);

		Iterator<com.vaadin.ui.Component> iterator = limitCostLayout.iterator();
		while (iterator.hasNext())
			((LimitCostView) iterator.next()).setAction(readOnly ? Action.VIEW : Action.EDIT);
	}
	
	private void setButtonVisible(boolean visible) {
		cancelBtn.setVisible(visible);
		editBtn.setVisible(visible);
		removeBtn.setVisible(visible);
		okBtn.setVisible(visible);
		addLimitCostBtn.setVisible(visible);
	}
	
	private void edit() {
		job.setName(Objects.toString(jobNameTf.getValue(), ""));
//		removeList = new HashSet<>();
//		removeList.addAll(job.getLimitCosts());
		job.getLimitCosts().clear();
		
		Iterator<com.vaadin.ui.Component> iterator = limitCostLayout.iterator();
		while (iterator.hasNext()) {
			LimitCost limitCost = ((LimitCostView) iterator.next()).getLimitCost();
			limitCost.setJob(job);
			job.getLimitCosts().add(limitCost);
		}
	}
	
	private void backToGrid() {
		AbstractLayout parent = (AbstractLayout) getParent(); 
		parent.removeAllComponents();
		parent.addComponent(appContext.getBean(ChucVuJobGridView.class));
	}
}
