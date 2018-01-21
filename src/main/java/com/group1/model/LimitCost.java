package com.group1.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "limit_cost")
public class LimitCost implements Serializable {
	@Id
	@Column(name = "limit_cost_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "job_id")
	private Job job;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "chuc_vu_id")
	private ChucVu chucVu;

	@Column(name = "limit_cost")
	private int limitCost;

	public LimitCost() {
	}

	public LimitCost(Job job, ChucVu chucVu) {
		this.job = job;
		this.chucVu = chucVu;
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

	public ChucVu getChucVu() {
		return chucVu;
	}

	public void setChucVu(ChucVu chucVu) {
		this.chucVu = chucVu;
	}

	public int getLimitCost() {
		return limitCost;
	}

	public void setLimitCost(int limitCost) {
		this.limitCost = limitCost;
	}

	@Override
	public String toString() {
		return "LimitCost [job=" + job.getName() + ", chucVu=" + chucVu.getName() + ", limitCost=" + limitCost + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LimitCost other = (LimitCost) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
