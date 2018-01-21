package com.group1.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="emp_group")
public class Group {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "group_id")
	private int id;

	private String name;

//	@OneToMany(mappedBy="group")
//	private Set<Employee> employees;
	
	@OneToOne
	@JoinColumn(name = "leader_id")
	private Employee leader;
//
	@OneToOne
	@JoinColumn(name = "accountant_id")
	private Employee accountant;

	public Group() {
	}

	public Group(String name) {
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

	public Employee getLeader() {
		return leader;
	}

	public void setLeader(Employee leader) {
		this.leader = leader;
	}

	public Employee getAccountant() {
		return accountant;
	}

	public void setAccountant(Employee accountant) {
		this.accountant = accountant;
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", leader=" + leader.getName() + ", accountant=" + accountant.getName() + "]";
	}

}
