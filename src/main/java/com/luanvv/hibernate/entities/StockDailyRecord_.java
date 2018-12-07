package com.luanvv.hibernate.entities;

import java.time.LocalDate;
import java.util.UUID;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StockDailyRecord.class)
public abstract class StockDailyRecord_ {

	public static volatile SingularAttribute<StockDailyRecord, Long> id;
	public static volatile SingularAttribute<StockDailyRecord, UUID> uuid;
	public static volatile SingularAttribute<StockDailyRecord, Stock> stock;
	public static volatile SingularAttribute<StockDailyRecord, Float> priceOpen;
	public static volatile SetAttribute<StockDailyRecord, Float> priceClose;
	public static volatile SetAttribute<StockDailyRecord, Float> priceChange;
	public static volatile SetAttribute<StockDailyRecord, Long> volume;
	public static volatile SingularAttribute<StockDailyRecord, LocalDate> date;

}