package com.lazywg.assembly.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author gaowang
 *
 * SQL事务
 */
public class SQLTruncate implements AutoCloseable {

	private Connection connect;
	private Statement statement;
	
	public SQLTruncate(Connection conn) {
		this.connect = conn;
	}

	public void begin() throws SQLException {
		this.connect.setAutoCommit(false);
		this.connect.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
	}

	public void commit() throws SQLException {
		connect.commit();
	}

	public void rollback() {
		try {
			this.connect.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void close() throws Exception {
		if (statement != null) {
			statement.close();
		}
	}

	public Statement createStatement() throws SQLException {
		if (statement == null) {
			this.statement = this.connect.createStatement();
			return this.statement;
		}
		statement.clearBatch();
		return statement;
	}
}
