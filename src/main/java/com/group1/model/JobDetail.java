package com.group1.model;

import java.util.Date;
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
import javax.persistence.Table;

@Entity
@Table(name = "job_detail")
public class JobDetail {
	@Id
	@Column(name = "job_detail_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;

	@ManyToOne
	@JoinColumn(name = "job_id")
	private Job job;

	private int quantity;

	@Column(name = "proposed_cost")
	private int proposedCost;

	private int tax;

	@Column(name = "no_tax_cost")
	private int noTaxCost;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "plan_id")
	private Plan plan;

	@OneToMany(mappedBy = "jobDetail", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
	private Set<Bill> bills;

	public JobDetail() {
	}

	public JobDetail(Job job) {
		this.job = job;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getProposedCost() {
		return proposedCost;
	}

	public void setProposedCost(int proposedCost) {
		this.proposedCost = proposedCost;
	}

	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}

	public int getNoTaxCost() {
		return noTaxCost;
	}

	public void setNoTaxCost(int noTaxCost) {
		this.noTaxCost = noTaxCost;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public Set<Bill> getBills() {
		return bills;
	}

	public void setBills(Set<Bill> bills) {
		this.bills = bills;
	}

	@Override
	public String toString() {
		return "JobDetail [id=" + id + ", job=" + job.getName() + ", quantity=" + quantity + ", proposedCost="
				+ proposedCost + ", tax=" + tax + ", noTaxCost=" + noTaxCost + ", startDate=" + startDate + ", endDate="
				+ endDate + "]";
	}

}
