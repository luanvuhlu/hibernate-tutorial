package com.luanvv.hibernate.entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.LazyGroup;  

//@Cacheable  
//@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)  
@Entity
@Table(name = "user")
public class User extends AbstractEntity {

	@Column
	private LocalDate dateOfBirth;

	@Formula(value = "YEAR(CURRENT_DATE()) - YEAR(dateOfBirth)")
	private int age;
	
	@Column(name = "USER_NAME")
	private String username;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_date")
	private Date createdDate;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private List<Stock> stock;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public List<Stock> getStock() {
		return stock;
	}
	
	public void setStock(List<Stock> stock) {
		this.stock = stock;
	}
	
	public int getAge() {
		return age;
	}
	
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
}