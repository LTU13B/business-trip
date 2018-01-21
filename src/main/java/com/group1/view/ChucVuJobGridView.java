package com.group1.view;

import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.group1.component.ChucVuJobGridDesign;
import com.group1.component.ChucvuJobDesign;
import com.group1.model.Job;
import com.group1.repository.JobRepository;
import com.group1.utils.Action;
import com.vaadin.ui.AbstractLayout;

@Component
@Scope("prototype")
public class ChucVuJobGridView extends ChucVuJobGridDesign {

	@Autowired
	private JobRepository jobRepository; 
	
	@Autowired
	private ApplicationContext appContext;
	
	@PostConstruct
	private void init() {
		setCaption("Danh sách công việc");
		grid.getColumn("id").setWidth(100.0);
		
		HashSet<Job> jobs = new HashSet<>();
		Iterable<Job> iterable = jobRepository.findAll();
		iterable.forEach(jobs::add);
		
		grid.setItems(jobs);
		grid.sort("id");
		
		grid.addSelectionListener(event->{
			Job selectedJob = event.getAllSelectedItems().iterator().next();
			ChucVuJobView chucVuJobView = appContext.getBean(ChucVuJobView.class);
			chucVuJobView.setJob(selectedJob);
			
			AbstractLayout parent = (AbstractLayout) getParent(); 
			parent.removeAllComponents();
			parent.addComponent(chucVuJobView);
		});
		
		addBtn.addClickListener(event->{
			ChucVuJobView chucVuJobView = appContext.getBean(ChucVuJobView.class);
			chucVuJobView.setAction(Action.ADD);
			
			AbstractLayout parent = (AbstractLayout) getParent(); 
			parent.removeAllComponents();
			parent.addComponent(chucVuJobView);
		});
	}
}

