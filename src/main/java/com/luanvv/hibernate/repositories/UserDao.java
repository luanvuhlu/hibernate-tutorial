package com.luanvv.hibernate.repositories;

import com.luanvv.hibernate.entities.User;

public class UserDao extends AbstractDao {

	public User insert(User user) {
		doInTransaction(session -> {
			session.save(user);
		});
		return user;
	}
	
	public User find(Long id) {
		return doInTransaction(session -> {
			return session.find(User.class, id);
		});
	}

	@Override
	protected Class<?>[] entities() {
		return new Class<?>[] { User.class };
	}
}
