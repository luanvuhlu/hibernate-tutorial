package com.luanvv.hibernate.repositories;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.luanvv.hibernate.entities.User;

public class UserDaoTest {

	private UserDao userDao;
	@Before
	public void init() {
		userDao = new UserDao();
	}
	@Test
	public void testFormular() {
		User user = new User();
		user.setUsername("Luanvv");
		user.setDateOfBirth(LocalDate.of(1992, 12, 30));
		userDao.insert(user);
		User newUser = userDao.find(user.getId());
		assertEquals(26, newUser.getAge());
	}
}
