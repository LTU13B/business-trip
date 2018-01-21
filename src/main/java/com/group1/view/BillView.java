package com.group1.view;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.group1.component.BillDesign;
import com.group1.model.Bill;
import com.group1.utils.Action;
import com.vaadin.ui.AbstractLayout;

@Component
@Scope("prototype")
public class BillView extends BillDesign {

	private Bill bill;
	
	@PostConstruct
	private void init() {
		removeBillBtn.addClickListener(event -> {
			((AbstractLayout) getParent()).removeComponent(this);
		});
	}
	
	public Bill getBill() {
		if (bill==null)
			bill = new Bill();
		bill.setName(billNameTf.getValue());
		bill.setTax(Integer.parseInt(billTaxTf.getValue()));
		bill.setNoTaxCost(Integer.parseInt(billNoTaxCostTf.getValue()));
		return bill;
	}
	
	public void setBill(Bill bill) {
		this.bill = bill;
		billNameTf.setValue(bill.getName());
		billTaxTf.setValue(bill.getTax()+"");
		billNoTaxCostTf.setValue(bill.getNoTaxCost()+"");
	}
	
	public void setReadOnly(boolean readOnly) {
		billNameTf.setReadOnly(readOnly);
		billTaxTf.setReadOnly(readOnly);
		billNoTaxCostTf.setReadOnly(readOnly);
	}

	public void setAction(Action action) {
		switch(action) {
		case VIEW:
			removeBillBtn.setVisible(false);
			setReadOnly(true);
			break;
		case EDIT:
			removeBillBtn.setVisible(true);
			setReadOnly(false);
			break;
		}
		
	}
}
