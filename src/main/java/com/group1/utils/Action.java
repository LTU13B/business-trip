package com.group1.utils;

public enum Action {
	VIEW(1),
	EDIT(2),
	ADD(3);
	
	private final int id;
	
	private Action(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
