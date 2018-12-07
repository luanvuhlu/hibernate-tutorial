package com.luanvv.hibernate.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyGroup;

import com.luanvv.hibernate.converters.StockStatusConverter;

@Cacheable  
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "stock")
public class Stock {

	@Column(name = "STOCK_CODE", nullable = false, length = 10)
	private String stockCode;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@LazyGroup(value = "images")
	@Column(name = "COVER_IMAGE")
	private byte[] coverImage;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@LazyGroup(value = "images")
	@Column(name = "COVER_IMAGE_2")
	private byte[] coverImage2;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stock")
	private List<StockDailyRecord> stockDailyRecords;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = true)
	private User user;
	
	@Id
	private int id;
	
//	@CreationTimestamp
	@Column(name = "created_time", updatable = false)
	private LocalDateTime createdTime;
	
//	@UpdateTimestamp
	@Column(name = "updated_time")
	private LocalDateTime updatedTime;
	
//	@Size(min = 3, max = 20)
//	@Column(name = "STOCK_CODE", nullable = false, length = 10)
//	private String stockCode;
	
//	@FutureOrPresent
	private LocalDate date;
	
//	@Size(min = 10, max = 200)
	@Column(name = "STOCK_NAME", nullable = false, length = 20)
	private String stockName;
	
	@Convert(converter = StockStatusConverter.class)
    private StockStatus status;
	
	public Stock() {
		// Empty
	}
	public Stock(String stockCode, String stockName) {
		this.stockCode = stockCode;
		this.stockName = stockName;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public List<StockDailyRecord> getStockDailyRecords() {
		return stockDailyRecords;
	}

	public void setStockDailyRecords(List<StockDailyRecord> stockDailyRecords) {
		this.stockDailyRecords = stockDailyRecords;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public StockStatus getStatus() {
		return status;
	}

	public void setStatus(StockStatus status) {
		this.status = status;
	}

	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public byte[] getCoverImage() {
		return coverImage;
	}
	
	public void setCoverImage(byte[] coverImage) {
		this.coverImage = coverImage;
	}
	
	public byte[] getCoverImage2() {
		return coverImage2;
	}
	
	public void setCoverImage2(byte[] coverImage2) {
		this.coverImage2 = coverImage2;
	}
}
