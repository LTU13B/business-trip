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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "chuc_vu")
public class ChucVu {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "chuc_vu_id")
	private int id;

	private String name;

	@OneToMany(mappedBy = "chucVu", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<LimitCost> limitCosts;

	public void test() {
		Iterator iterator = limitCosts.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

	public ChucVu() {
	}

	public ChucVu(String name) {
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
		return "ChucVu [id=" + id + ", name=" + name + "]";
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
		ChucVu other = (ChucVu) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
