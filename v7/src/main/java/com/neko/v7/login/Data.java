package com.neko.v7.login;

import java.util.List;

public interface Data<T> {
	T findOne(long session);
	List<T> findAll();
	int count();
	T save(T entity);
	void delete(long session);
}
