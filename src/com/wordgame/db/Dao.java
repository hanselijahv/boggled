package com.wordgame.db;

import java.util.List;

public interface Dao<T> {
	 List<T> getAll();
	 boolean insert(T t);
	 boolean update(T t, String[] params);
	 boolean delete(T t);
}
