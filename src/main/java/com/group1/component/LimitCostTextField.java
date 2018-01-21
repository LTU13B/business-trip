package com.group1.component;

import com.group1.model.LimitCost;
import com.vaadin.ui.TextField;

public class LimitCostTextField extends TextField {
	
	private LimitCost limitCost;

	public LimitCost getLimitCost() {
		return limitCost;
	}

	public void setLimitCost(LimitCost limitCost) {
		this.limitCost = limitCost;
		setValue(limitCost.getLimitCost()+"");	
	}
	
	
}
