package com.group1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "plan_status")
public class PlanStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "plan_status_id")
	private int id;

	private String name;

	public PlanStatus() {
	}

	public PlanStatus(String name) {
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

	@Override
	public String toString() {
		return "PlanStatus [id=" + id + ", name=" + name + "]";
	}

}
