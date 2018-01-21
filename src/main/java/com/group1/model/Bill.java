package com.group1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Bill {
	@Id
	@Column(name = "bill_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String name;

	private int tax;

	@Column(name = "no_tax_cost")
	private int noTaxCost;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "job_detail_id")
	private JobDetail jobDetail;

	public Bill() {
	}

	public Bill(String name, int tax, int noTaxCost) {
		this.name = name;
		this.tax = tax;
		this.noTaxCost = noTaxCost;
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

	public JobDetail getJobDetail() {
		return jobDetail;
	}

	public void setJobDetail(JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}

	@Override
	public String toString() {
		return "Bill [id=" + id + ", name=" + name + ", tax=" + tax + ", noTaxCost=" + noTaxCost + "]";
	}

}
