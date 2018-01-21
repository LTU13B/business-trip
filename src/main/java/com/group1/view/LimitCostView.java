package com.group1.view;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.group1.component.LimitCostDesign;
import com.group1.model.ChucVu;
import com.group1.model.Job;
import com.group1.model.LimitCost;
import com.group1.repository.ChucVuRepository;
import com.group1.utils.Action;

@Component
@Scope("prototype")
public class LimitCostView extends LimitCostDesign {

	private Job job;
	
	private LimitCost limitCost;
	
	@Autowired
	private ChucVuRepository chucvuRepository;

	@PostConstruct
	private void init() {

		Set<ChucVu> chucVus = new HashSet<ChucVu>();
		Iterable<ChucVu> iterable = chucvuRepository.findAll();
		iterable.forEach(chucVus::add);

		chucvuCbo.setItems(chucVus);
		chucvuCbo.setItemCaptionGenerator(ChucVu::getName);
	}
	
	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public void setAction(Action action) {
		switch(action) {
		case VIEW:
			chucvuCbo.setReadOnly(true);
			limitCostTf.setReadOnly(true);
			break;
		case EDIT:
			chucvuCbo.setReadOnly(false);
			limitCostTf.setReadOnly(false);
			break;
		}
	}
	
	public void show(LimitCost limitCost) {
		this.limitCost = limitCost;
		chucvuCbo.setValue(limitCost.getChucVu());
		limitCostTf.setValue(Objects.toString(limitCost.getLimitCost(), ""));
	}

	public LimitCost getLimitCost() {
		if (limitCost==null)
			limitCost = new LimitCost(job, chucvuCbo.getValue());
		limitCost.setChucVu(chucvuCbo.getValue());
		limitCost.setLimitCost(Integer.parseInt(limitCostTf.getValue()));
		return limitCost;
	}
}
