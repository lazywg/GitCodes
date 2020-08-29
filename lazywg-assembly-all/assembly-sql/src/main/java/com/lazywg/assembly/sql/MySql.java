package com.lazywg.assembly.sql;


import com.lazywg.assembly.sql.config.SQLConfig;
import com.lazywg.assembly.sql.entity.Column;
import com.lazywg.assembly.sql.entity.Table;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

public class MySql extends SQL {

	private static Logger logger = Logger.getLogger(MySql.class);

	protected SQLConfig sqlConfig;

	public MySql(SQLConfig config) {
		this.sqlConfig = config;
		this.batchSize = config.getBatchSize() < 1 ? 1000 : config.getBatchSize();
	}

	@Override
	public String getSQLDriver() {
		if (StringUtils.isBlank(sqlConfig.getJdbcDriver())) {
			sqlConfig.setJdbcDriver("com.mysql.jdbc.Driver");
		}
		// logger.info(String.format("%s|%s",
		// "getSQLDriver",sqlConfig.getJdbcDriver()));
		// 可通过配置读取
		return sqlConfig.getJdbcDriver();
	}

	@Override
	public String getConnectUri() {
		if (StringUtils.isBlank(sqlConfig.getConnectUri())) {
			sqlConfig.setConnectUri(String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8&useSSL=false", sqlConfig.getDbIp(), sqlConfig.getDbPort(),
					StringUtils.isBlank(sqlConfig.getDbName()) ? "mysql" : sqlConfig.getDbName()));
		}
		logger.info(String.format("%s|%s", "getConnectUri", sqlConfig.getConnectUri()));
		return sqlConfig.getConnectUri();
	}

	@Override
	public Connection getSQLConnection() throws ClassNotFoundException, SQLException {
		Class.forName(getSQLDriver());
		// 声明connection对象
		return DriverManager.getConnection(getConnectUri(), sqlConfig.getDbUser(), sqlConfig.getDbPwd());
	}

	@SuppressWarnings("unchecked")
	public <T> T executeScalar(String sqlStr, Class<T> cls) throws SQLException {
		if (openConnect()) {
			// 使用Connection创建一个Statement
			Statement state = connect.createStatement();
			// 执行sql语句
			ResultSet resultSet = state.executeQuery(sqlStr);
			try {
				Object first = null;
				if (resultSet.next()) {
					first = resultSet.getObject(1, cls);
				}
				resultSet.close();
				state.close();
				return (T) first;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获取所有表
	 * 
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<Table> getTables() throws SQLException {
		String sql = String.format("select TABLE_NAME tableName,TABLE_COMMENT tableComment  from  INFORMATION_SCHEMA.TABLES where table_schema='%s'", sqlConfig.getDbName());
		return executeResultSet(sql, Table.class);
	}

	/**
	 * 获取表下所有字段
	 * 
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<Column> getTableColumns(String tableName) throws SQLException {
		String sql = String.format("select COLUMN_NAME columnName,COLUMN_KEY columnKey,DATA_TYPE columnType,COLUMN_COMMENT columnComment  from  INFORMATION_SCHEMA.Columns where table_schema='%s' and table_name='%s'",
				sqlConfig.getDbName(), tableName);
		return executeResultSet(sql, Column.class);
	}

}
