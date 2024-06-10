package net.team6.boggled.common.db;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
	 List<T> getAll() throws SQLException;
	 boolean insert(T t) throws SQLException;
	 boolean update(T t, String[] params) throws SQLException;
	 boolean delete(T t) throws SQLException;
}
