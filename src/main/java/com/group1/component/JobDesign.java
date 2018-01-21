package com.group1.component;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.declarative.Design;

/** 
 * !! DO NOT EDIT THIS FILE !!
 * 
 * This class is generated by Vaadin Designer and will be overwritten.
 * 
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class JobDesign extends VerticalLayout {
	protected Button removeJobBtn;
	protected ComboBox<com.group1.model.Job> jobListCbo;
	protected TextField limitCostTf;
	protected TextField quantityTf;
	protected TextField proposedCostTf;
	protected DateField startDateDf;
	protected DateField endDateDf;
	protected Button addBillBtn;
	protected VerticalLayout billLayout;

	public JobDesign() {
		Design.read(this);
	}
}
