package com.group1.utils;

public enum PlanViewMode {
	PROPOSE_PLAN(1),
	PLAN_PAYMENT(2);
	
	private int id;
	
	private PlanViewMode(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
