package com.luanvv.hibernate.repositories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.CacheMode;
import org.junit.Before;
import org.junit.Test;

import com.luanvv.hibernate.entities.Stock;
import com.luanvv.hibernate.entities.StockStatus;
import com.luanvv.hibernate.entities.Stock_;

public class StockDaoTest {
	private static final Logger log = LogManager.getLogger(StockDaoTest.class);

	private StockDao stockDao;
	
	@Before
	public void init() {
		stockDao = new StockDao();
	}
	
	@Test
	public void testInsertMultiJDBC() {
		long startNanos = System.nanoTime();
		stockDao.doInTransaction(session -> {
			session.doWork(conn -> {
				IntStream.range(1, 1_000 + 1).forEach(j -> {
					String sql = "insert into PUBLIC.stock(created_time, date, status, STOCK_CODE, STOCK_NAME, updated_time, USER_ID, id) values ";
					sql += IntStream.range(1, 1_000 + 1)
							.mapToObj(i -> "(null, '2018-12-25', 0, 'A', 'Apple "+(j*i)+"', null, null, "+(1_000*j - 1_000 + i)+")")
							.collect( Collectors.joining( "," ));
					try(PreparedStatement prepareStatement = conn.prepareStatement(sql)){
						prepareStatement.executeLargeUpdate();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				});
			});
		});
		log.info(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos));
	}
	
	@Test
	public void testCacheHQL() {
		testInsert();
		stockDao.doInTransaction(session -> {
			session.createQuery("INSERT INTO Stock(stockCode, stockName, id) SELECT stockCode, stockName, id + 1 FROM Stock ")
			.executeUpdate();
			log.info("Insert 2");
			log.info("Find 2");
			session.find(Stock.class, 2);
		});
		log.info("Clean");
		stockDao.doInTransaction(session -> {
			Stock stock = new Stock();
			stock.setId(1);
			session.delete(stock);
			Stock stock2 = new Stock();
			stock2.setId(2);
			session.delete(stock2);
		});
	}
	
	@Test
	public void testApplicationCache() {
		log.info("****Insert 1****");
		stockDao.doInTransaction(session -> {
			Stock stock1 = newStock();
			stock1.setId(1);
			stock1.setCoverImage("test string".getBytes());
			stock1.setStockName("Apple 1");
			session.persist(stock1);
		});
		log.info("****Try find all by HQL****");
		stockDao.doInTransaction(session -> {
			session
				.createQuery("FROM Stock ", Stock.class)
				.setCacheable(true)
				.list()
				.forEach(stock -> log.info(stock.getStockName()));
		});
		log.info("****Insert 2****");
		insertStock2();
		log.info("****Try find all by HQL again****");
		stockDao.doInTransaction(session -> {
			session
				.createQuery("FROM Stock ", Stock.class)
				.setCacheable(true)
				.list()
				.forEach(stock -> log.info(stock.getStockName()));
		});
		log.info("****Try find all by Criteria****");
		stockDao.doInTransaction(session -> {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Stock> q = cb.createQuery(Stock.class);
			Root<Stock> a = q.from(Stock.class);
			session.createQuery(q)
			.setCacheable(true)
			.getResultList()
			.forEach(stock -> log.info(stock.getStockName()));
		});
		log.info("****Try find all by HQL again but difference condition****");
		stockDao.doInTransaction(session -> {
			session
				.createQuery("FROM Stock WHERE id > 0 ", Stock.class)
				.setCacheable(true)
				.list()
				.forEach(stock -> log.info(stock.getStockName()));
		});
		log.info("Clean");
		stockDao.doInTransaction(session -> {
			Stock stock = new Stock();
			stock.setId(1);
			session.delete(stock);
			Stock stock2 = new Stock();
			stock2.setId(2);
			session.delete(stock2);
		});
	}
	
	@Test
	public void testQueryCache() {
		log.info("****Insert 1****");
		stockDao.doInTransaction(session -> {
			Stock stock1 = newStock();
			stock1.setId(1);
			stock1.setCoverImage("test string".getBytes());
			stock1.setStockName("Apple 1");
			session.persist(stock1);
		});
		log.info("****Try find all by HQL****");
		stockDao.doInTransaction(session -> {
			session
				.createQuery("FROM Stock ", Stock.class)
				.setCacheable(true)
				.list()
				.forEach(stock -> log.info(stock.getStockName()));
		});
		log.info("****Insert 2****");
		insertStock2();
		log.info("****Try find all by HQL again****");
		stockDao.doInTransaction(session -> {
			session
				.createQuery("FROM Stock ", Stock.class)
				.setCacheable(true)
				.list()
				.forEach(stock -> log.info(stock.getStockName()));
		});
		log.info("****Try find all by Criteria****");
		stockDao.doInTransaction(session -> {
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Stock> q = cb.createQuery(Stock.class);
			Root<Stock> a = q.from(Stock.class);
			session.createQuery(q)
			.setCacheable(true)
			.getResultList()
			.forEach(stock -> log.info(stock.getStockName()));
		});
		log.info("****Try find all by HQL again but difference condition****");
		stockDao.doInTransaction(session -> {
			session
				.createQuery("FROM Stock WHERE id > 0 ", Stock.class)
				.setCacheable(true)
				.list()
				.forEach(stock -> log.info(stock.getStockName()));
		});
		log.info("Clean");
		stockDao.doInTransaction(session -> {
			Stock stock = new Stock();
			stock.setId(1);
			session.delete(stock);
			Stock stock2 = new Stock();
			stock2.setId(2);
			session.delete(stock2);
		});
	}

	private void insertStock2() {
		stockDao.doInTransaction(session -> {
			Stock stock2 = newStock();
			stock2.setId(2);
			stock2.setCoverImage("test string 2".getBytes());
			stock2.setStockName("Apple 2");
			session.persist(stock2);
		});
	}
	
	@Test
	public void testInsertMultiBatchMultiTrans() {
		int size = 2_000;
		long startNanos = System.nanoTime();
		stockDao.doInTransaction(session -> {
			for(int i =0; i< size; i++) {
				if( i % 200 == 0 && i > 0) {
					session.getTransaction().commit();
					session.getTransaction().begin();
				      session.clear();
				   }
				Stock stock = new Stock();
				stock.setId(i + 1);
				stock.setStockCode("A");
				stock.setDate(LocalDate.of(2018, 12, 25));
				stock.setStatus(StockStatus.ON);
				stock.setStockName("Apple " + i);
				session.persist(stock);
			};
		});
		log.info(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos));
		startNanos = System.nanoTime();
		stockDao.doInTransaction(session -> {
			for(int i =0; i< size; i++) {
				Stock stock = session.find(Stock.class, i);
//				log.info(stock.getStockName());
			}
			return null;
		});
		log.info(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos));
	}
	
	@Test
	public void testInsertMultiBatchOneTrans() {
		long startNanos = System.nanoTime();
		stockDao.doInTransaction(session -> {
			for(int i =0; i< 20_000; i++) {
				if( i % 200 == 0 && i > 0) {
					session.flush();
				    session.clear();
				   }
				Stock stock = new Stock();
				stock.setId(i + 1);
				stock.setStockCode("A");
				stock.setDate(LocalDate.of(2018, 12, 25));
				stock.setStatus(StockStatus.ON);
				stock.setStockName("Apple " + i);
				session.persist(stock);
			};
		});
		log.info(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos));
		startNanos = System.nanoTime();
		stockDao.doInTransaction(session -> {
			for(int i =0; i< 20_000; i++) {
				Stock stock = session.find(Stock.class, i);
//				log.info(stock.getStockName());
			}
			return null;
		});
		log.info(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos));
	}
	
	@Test
	public void testInsert() {
		Stock stock = new Stock();
		stock.setId(1);
		stock.setStockCode("A");
		stock.setDate(LocalDate.of(2018, 12, 25));
		stock.setStatus(StockStatus.ON);
		stock.setStockName("Apple");
		stockDao.insert(stock);
	}
	
	public Stock newStock() {
		Stock stock = new Stock();
		stock.setId(1);
		stock.setStockCode("A");
		stock.setDate(LocalDate.of(2018, 12, 25));
		stock.setStatus(StockStatus.ON);
		stock.setStockName("Apple");
		return stock;
	}
	
	@Test
	public void testFormular() {
		Stock stock = new Stock();
		stock.setStockCode("A");
		stock.setDate(LocalDate.of(2018, 12, 25));
		stock.setStatus(StockStatus.ON);
		stock.setStockName("Apple");
		stockDao.insert(stock);
		Stock newStock = stockDao.find(stock.getId());
//		log.info(newStock.getCreatedTime());
//		log.info(newStock.getUpdatedTime());
		stock.setStockName("Pineapple");
		stockDao.update(stock);
		newStock = stockDao.find(stock.getId());
//		log.info(newStock.getStatus());
//		log.info(newStock.getCreatedTime());
//		log.info(newStock.getUpdatedTime());
	}
}
