package com.group1.utils;

public enum Role {
	ADMIN(1),
	LEADER(2),
	ACCOUNTANT(3);
	
	private int id;
	
	private Role(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
