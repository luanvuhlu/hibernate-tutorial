package com.luanvv.hibernate.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Stock.class)
public abstract class Stock_ {

	public static volatile SingularAttribute<Stock, Long> id;
	public static volatile SingularAttribute<Stock, UUID> uuid;
	public static volatile SingularAttribute<Stock, String> stockCode;
	public static volatile SingularAttribute<Stock, String> stockName;
	public static volatile SetAttribute<Stock, User> user;
	public static volatile SingularAttribute<Stock, LocalDateTime> createdTime;
	public static volatile SingularAttribute<Stock, LocalDateTime> updatedTime;
	public static volatile SingularAttribute<Stock, StockStatus> status;

}