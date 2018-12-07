package com.luanvv.hibernate.entities;

public enum StockStatus {

	UNKNOW(-1),
	ON(0), 
	OFF(1);
	
	private int value;

	private StockStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	public static StockStatus fromValue(int value) {
		for (StockStatus status : StockStatus.values()) {
			if (status.getValue() == value) {
				return status;
			}
		}
		return UNKNOW;
	}
}
