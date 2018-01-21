package com.group1.model;

import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

@Entity
public class Plan {
	@Id
	@Column(name = "plan_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;

	@ManyToOne
	@JoinColumn(name = "emp_id")
	private Employee emp;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "check_date")
	private Date checkDate;

	@ManyToOne
	@JoinColumn(name = "status_id")
	private PlanStatus status;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	private String purpose;
	private String place;
	private String partner;

	private boolean advance;

	@Column(name = "advance_cost")
	private Long advanceCost;
	private boolean arised;

	@OneToMany(mappedBy = "plan", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
	private Set<JobDetail> jobDetails;

	public Plan() {
	}

	public Plan(String name) {
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

	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public PlanStatus getStatus() {
		return status;
	}

	public void setStatus(PlanStatus status) {
		this.status = status;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public Long getAdvanceCost() {
		return advanceCost;
	}

	public void setAdvanceCost(Long advanceCost) {
		this.advanceCost = advanceCost;
	}

	public boolean isAdvance() {
		return advance;
	}

	public void setAdvance(boolean advance) {
		this.advance = advance;
	}

	public boolean isArised() {
		return arised;
	}

	public void setArised(boolean arised) {
		this.arised = arised;
	}

	public Set<JobDetail> getJobDetails() {
		return jobDetails;
	}

	public void setJobDetails(Set<JobDetail> jobDetails) {
		this.jobDetails = jobDetails;
	}

	@Override
	public String toString() {
		return "Plan [id=" + id + ", name=" + name + ", emp=" + emp.getName() + ", createDate=" + createDate
				+ ", checkDate=" + checkDate + ", status=" + status.getName() + ", project=" + project.getName()
				+ ", purpose=" + purpose + ", place=" + place + ", partner=" + partner + ", advance=" + advance
				+ ", advanceCost=" + advanceCost + ", arised=" + arised + "]";
	}

}
