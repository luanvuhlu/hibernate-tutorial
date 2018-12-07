package com.luanvv.hibernate.repositories;

import com.luanvv.hibernate.entities.Stock;

public class StockDao extends AbstractDao {

	public Stock insert(Stock stock) {
		doInTransaction(session -> {
			session.save(stock);
		});
		return stock;
	}
	
	public Stock update(Stock stock) {
		doInTransaction(session -> {
			session.update(stock);
		});
		return stock;
	}
	
	public Stock find(int id) {
		return doInTransaction(session -> {
			return session.find(Stock.class, id);
		});
	}

	@Override
	protected Class<?>[] entities() {
		return new Class<?>[] { Stock.class };
	}
}
