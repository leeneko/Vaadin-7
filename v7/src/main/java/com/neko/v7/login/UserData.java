package com.neko.v7.login;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class UserData implements Data<User> {
	private static volatile UserData INSTANCE = null;
	private Map<Long, User> users;
	private AtomicLong nextId;
	
	private UserData() {
		nextId = new AtomicLong();
		users = new LinkedHashMap<>();
	}
	
	public synchronized static UserData getInstance() {
		if (INSTANCE == null) {
			synchronized (UserData.class) {
				if (INSTANCE == null) {
					INSTANCE = new UserData();
				}
			}
		}
		return INSTANCE;
	}

	@Override
	public synchronized User findOne(long session) {
		User user = users.get(session);
		if (user != null) {
			return user;
		}
		return new User();
	}

	@Override
	public synchronized List<User> findAll() {
		return Collections.unmodifiableList(new ArrayList<>(users.values()));
	}

	@Override
	public int count() {
		return users.size();
	}

	@Override
	public synchronized User save(User user) {
		User checkUser;
		if (user.getSession() == null) {
			checkUser = findById(user.getId());
			if (checkUser.getId() != null) {
				throw new IllegalArgumentException("Duplicated user id");
			}
			user.setSession(nextId.incrementAndGet());
			users.put(user.getSession(), user);
			return user;
		}
		checkUser = findById(user.getId());
		if (users.containsKey(user.getSession())) {
			if (user.getSession() != checkUser.getSession() && user.getId().equals(checkUser.getId())) {
				throw new IllegalArgumentException("Duplicated user id");
			}
			user.setSession(nextId.incrementAndGet());
			users.put(user.getSession(), user);
			return user;
		}
		throw new IllegalArgumentException("No user with session " + user.getSession() + " found");
	}

	@Override
	public void delete(long session) {
		User user = findOne(session);
		if (user == null) {
			throw new IllegalArgumentException("User with Session " + session + " not found");
		}
		users.remove(user.getSession());
	}
	public synchronized User findById(String id) {
		List<User> users = findAll();
		for (User user: users) {
			if (user.getId().equals(id)) {
				return user;
			}
		}
		return new User();
	}
	public synchronized User findByIdAndPw(String id, String pw) {
		List<User> users = findAll();
		for(User user: users) {
			if(user.getId().equals(id) && user.getPw().equals(pw)) {
				return user;
			}
		}
		return new User();
	}
}
