package com.luanvv.hibernate.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stock_daily_record")
public class StockDailyRecord extends AbstractEntity {

	@ManyToOne
	@JoinColumn(name = "STOCK_ID", nullable = false)
	private Stock stock;
	
	@Column(name = "PRICE_OPEN", precision = 6)
	private Float priceOpen;
	
	@Column(name = "PRICE_CLOSE", precision = 6)
	private Float priceClose;
	
	@Column(name = "PRICE_CHANGE", precision = 6)
	private Float priceChange;
	
	@Column(name = "VOLUME")
	private Long volume;
	
	@Column(name = "DATE", nullable = false, length = 10)
	private LocalDate date;

	public StockDailyRecord() {
		// Empty
	}
	
	public StockDailyRecord(Stock stock, LocalDate date) {
		this.stock = stock;
		this.date = date;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Float getPriceOpen() {
		return priceOpen;
	}

	public void setPriceOpen(Float priceOpen) {
		this.priceOpen = priceOpen;
	}

	public Float getPriceClose() {
		return priceClose;
	}

	public void setPriceClose(Float priceClose) {
		this.priceClose = priceClose;
	}

	public Float getPriceChange() {
		return priceChange;
	}

	public void setPriceChange(Float priceChange) {
		this.priceChange = priceChange;
	}

	public Long getVolume() {
		return volume;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

}
