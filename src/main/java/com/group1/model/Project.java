package com.group1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "project_id")
	private int id;

	private String name;

	@Column(name = "usage_cost")
	private long usageCost;

	@Column(name = "limit_cost")
	private long limitCost;

	public Project() {
	}

	public Project(String name) {
		super();
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getUsageCost() {
		return usageCost;
	}

	public void setUsageCost(long usageCost) {
		this.usageCost = usageCost;
	}

	public long getLimitCost() {
		return limitCost;
	}

	public void setLimitCost(long limitCost) {
		this.limitCost = limitCost;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", usageCost=" + usageCost + ", limitCost=" + limitCost + "]";
	}

}
