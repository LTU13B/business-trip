package com.group1.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Job {
	@Id
	@Column(name = "job_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;

	@OneToMany(mappedBy = "job", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private Set<LimitCost> limitCosts;

	public Job() {
	}

	public Job(String name) {
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

	public Set<LimitCost> getLimitCosts() {
		return limitCosts;
	}

	public void setLimitCosts(Set<LimitCost> limitCosts) {
		this.limitCosts = limitCosts;
	}

	@Override
	public String toString() {
		return "Job [id=" + id + ", name=" + name + "]";
	}

}
