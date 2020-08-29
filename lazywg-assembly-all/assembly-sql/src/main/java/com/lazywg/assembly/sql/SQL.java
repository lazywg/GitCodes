package com.lazywg.assembly.sql;

import com.lazywg.assembly.sql.utils.AutoMapper;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class SQL implements ISQL {

	private static Logger logger = Logger.getLogger(SQL.class);

	protected int batchSize = 1000;

	/**
	 * 连接
	 */
	protected Connection connect;

	/**
	 * 获取驱动描述
	 * 
	 * @return
	 */
	protected abstract String getSQLDriver();

	/**
	 * 获取连接uri
	 * 
	 * @return
	 */
	protected abstract String getConnectUri();

	/**
	 * 获取连接
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	protected abstract Connection getSQLConnection() throws ClassNotFoundException, SQLException;

	public boolean isClosed() {
		if (connect == null) {
			return true;
		}
		try {
			return connect.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean openConnect() {
		if (isClosed()) {
			try {
				connect = getSQLConnection();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				logger.error("连接数据库异常。", e);
			}
		}
		return true;
	}

	public void close() throws SQLException {
		if (!isClosed()) {
			connect.close();
		}
	}

	public int getBatchSize() {
		return this.batchSize;
	}

	public int executeNonQuery(String sqlStr) throws SQLException {
		if (openConnect()) {
			// 使用Connection创建一个Statement
			Statement state = connect.createStatement();

			// 执行SQL（insert/update/delete）语句，返回数据库影响的行数
			int rows = state.executeUpdate(sqlStr);

			state.close();
			return rows;
		}
		return 0;
	}

	public <T> List<T> executeResultSet(String sqlStr, Class<T> cls) throws SQLException {
		if (openConnect()) {
			// 使用Connection创建一个Statement
			Statement state = connect.createStatement();

			ResultSet resultSet = state.executeQuery(sqlStr);

			List<T> entityList = AutoMapper.entityListMapper(resultSet, cls);

			state.close();
			resultSet.close();
			return entityList;
		}
		return null;
	}

	public long executeScalar(String sqlStr) throws SQLException {
		return executeScalar(sqlStr, Long.class);
	}

	public int executeNonQuery(String sqlStr, SQLTruncate tran) throws SQLException {
		Statement statement = tran.createStatement();
		int rows = statement.executeUpdate(sqlStr);
		return rows;
	}

	public int executeBatch(List<String> sqlStrs, SQLTruncate tran) throws SQLException {
		Statement statement = tran.createStatement();
		for (String sqlStr : sqlStrs) {
			statement.addBatch(sqlStr);
		}
		int[] rows = statement.executeBatch();
		int count = 0;
		for (int num : rows) {
			count += num;
		}
		return count;
	}

	public SQLTruncate createTruncate() throws ClassNotFoundException, SQLException {
		if (openConnect()) {
			return new SQLTruncate(this.connect);
		}
		return new SQLTruncate(getSQLConnection());
	}
}
