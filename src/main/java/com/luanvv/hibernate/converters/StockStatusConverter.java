package com.luanvv.hibernate.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.luanvv.hibernate.entities.StockStatus;

@Converter(autoApply = true)
public class StockStatusConverter implements AttributeConverter<StockStatus, Integer> {

	public Integer convertToDatabaseColumn(StockStatus value) {
		if (value == null) {
			return null;
		}
		return value.getValue();
	}

	public StockStatus convertToEntityAttribute(Integer value) {
		if (value == null) {
			return null;
		}
		return StockStatus.fromValue(value);
	}
}