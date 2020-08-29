package com.lazywg.assembly.sql;

import com.lazywg.assembly.sql.config.SQLConfig;
import com.lazywg.assembly.sql.entity.Column;
import com.lazywg.assembly.sql.entity.Table;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.List;

public class Oracle extends SQL {
	private static Logger logger = Logger.getLogger(Oracle.class);
	
	protected SQLConfig sqlConfig;

	public Oracle(SQLConfig config) {
		this.sqlConfig = config;
		this.batchSize = config.getBatchSize()<1?1000:config.getBatchSize();
	}

	@Override
	public String getSQLDriver() {
		if (StringUtils.isBlank(sqlConfig.getJdbcDriver())) {
			sqlConfig.setJdbcDriver("oracle.jdbc.driver.OracleDriver");
		}
		//logger.info(String.format("%s|%s", "getSQLDriver",sqlConfig.getJdbcDriver()));
		return sqlConfig.getJdbcDriver();
	}

	@Override
	public String getConnectUri() {
		if (StringUtils.isBlank(sqlConfig.getConnectUri())) {
			sqlConfig.setConnectUri(String.format("jdbc:oracle:thin:@%s:%s:ORCL", sqlConfig.getDbIp(), sqlConfig.getDbPort()));
		}
		logger.info(String.format("%s|%s", "getConnectUri",sqlConfig.getConnectUri()));
		return sqlConfig.getConnectUri();
	}

	@Override
	public Connection getSQLConnection() throws ClassNotFoundException, SQLException {
		Class.forName(getSQLDriver());
		String userName = sqlConfig.getDbUser();
		if (StringUtils.isBlank(userName) || userName.equalsIgnoreCase("sys")) {
			userName = "sys as sysdba";
		}
		// 声明connection对象
		return DriverManager.getConnection(getConnectUri(), userName,sqlConfig.getDbPwd());
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
					first = resultSet.getObject(1);
				}
				resultSet.close();
				state.close();
				
				if (first.getClass().equals(BigDecimal.class)) {
					Object val = convertToNumberOrBool((BigDecimal)first, cls);
					return (T)val;
				}
				
				if (cls.getName().equals(Date.class.getName())) {
					return (T)convertToDate(first);
				}
				
				return (T) first;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static Object convertToDate(Object val) {
		if (val instanceof Timestamp) {
			Timestamp tsm = (Timestamp) val;
			return new Date(tsm.getTime());
		}
		if (val instanceof java.sql.Date) {
			java.sql.Date date = (java.sql.Date) val;
			return new Date(date.getTime());
		}
		return val;
	}
	
	private static <T> Object convertToNumberOrBool(BigDecimal val, Class<T> cls) {
		String filedTypeName = cls.getName();
		
		if (filedTypeName.equalsIgnoreCase(Integer.class.getName()) || filedTypeName.equalsIgnoreCase(int.class.getName())) {
			return val.intValueExact();
		}
		if (filedTypeName.equalsIgnoreCase(Byte.class.getName()) || filedTypeName.equalsIgnoreCase(byte.class.getName())) {
			return val.byteValueExact();
		}
		if (filedTypeName.equalsIgnoreCase(Short.class.getName()) || filedTypeName.equalsIgnoreCase(short.class.getName())) {
			return val.shortValueExact();
		}
		if (filedTypeName.equalsIgnoreCase(Long.class.getName()) || filedTypeName.equalsIgnoreCase(long.class.getName())) {
			return val.longValueExact();
		}
		if (filedTypeName.equalsIgnoreCase(Double.class.getName()) || filedTypeName.equalsIgnoreCase(double.class.getName())) {
			return val.doubleValue();
		}
		if (filedTypeName.equalsIgnoreCase(Float.class.getName()) || filedTypeName.equalsIgnoreCase(float.class.getName())) {
			return val.floatValue();
		}
		if (filedTypeName.equalsIgnoreCase(Boolean.class.getName()) || filedTypeName.equalsIgnoreCase(boolean.class.getName())) {
			return val.intValue() == 0 ? false : true;
		}
		return val;
	}

	@Override
	public List<Table> getTables() throws SQLException {
		String sql = "select TABLE_NAME tableName,COMMENTS tableComment from user_tab_comments";
		return executeResultSet(sql, Table.class);
	}

	@Override
	public List<Column> getTableColumns(String tableName) throws SQLException {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("select d.COLUMN_NAME columnName,DATA_TYPE columnType,COMMENTS columnComment,CONSTRAINT_TYPE columnKey ");
		sqlBuilder.append("from ");
		sqlBuilder.append("( ");
		sqlBuilder.append(String.format("select COLUMN_NAME,DATA_TYPE from user_tab_columns where table_name = '%s' ",tableName));
		sqlBuilder.append(") d ");
		sqlBuilder.append("left join  ");
		sqlBuilder.append("( ");
		sqlBuilder.append(String.format("select COLUMN_NAME,COMMENTS from user_col_comments where table_name = '%s' ",tableName));
		sqlBuilder.append(") c on d.COLUMN_NAME = c.COLUMN_NAME ");
		sqlBuilder.append("left join ");
		sqlBuilder.append("( ");
		sqlBuilder.append("select COLUMN_NAME,CONSTRAINT_TYPE from user_constraints con,user_cons_columns col ");
		sqlBuilder.append(String.format("where con.constraint_name=col.constraint_name and col.table_name = '%s' ",tableName));
		sqlBuilder.append(") k on d.COLUMN_NAME = k.COLUMN_NAME ");
		return executeResultSet(sqlBuilder.toString(), Column.class);
	}
}
