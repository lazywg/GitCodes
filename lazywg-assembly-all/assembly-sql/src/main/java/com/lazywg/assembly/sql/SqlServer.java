package com.lazywg.assembly.sql;

import com.lazywg.assembly.sql.config.SQLConfig;
import com.lazywg.assembly.sql.entity.Column;
import com.lazywg.assembly.sql.entity.Table;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

public class SqlServer extends SQL {
	private static Logger logger = Logger.getLogger(SqlServer.class);

	protected SQLConfig sqlConfig;

	public SqlServer(SQLConfig config) {
		this.sqlConfig = config;
		this.batchSize = config.getBatchSize() < 1 ? 1000 : config.getBatchSize();
	}

	@Override
	public String getSQLDriver() {
		if (StringUtils.isBlank(sqlConfig.getJdbcDriver())) {
			sqlConfig.setJdbcDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			// sqlConfig.setJdbcDriver("net.sourceforge.jtds.jdbc.Driver");
		}
		// logger.info(String.format("%s|%s",
		// "getSQLDriver",sqlConfig.getJdbcDriver()));
		return sqlConfig.getJdbcDriver();
	}

	@Override
	public String getConnectUri() {
		if (StringUtils.isBlank(sqlConfig.getConnectUri())) {
			sqlConfig.setConnectUri(
					String.format("jdbc:sqlserver://%s:%s;DatabaseName=%s", sqlConfig.getDbIp(), sqlConfig.getDbPort(), StringUtils.isBlank(sqlConfig.getDbName()) ? "master" : sqlConfig.getDbName()));
			// sqlConfig.setConnectUri(String.format("jdbc:jtds:sqlserver://%s:%s;DatabaseName=%s",
			// sqlConfig.getDbIp(), sqlConfig.getDbPort(),
			// StringUtils.isBlank(sqlConfig.getDbName()) ? "master" :
			// sqlConfig.getDbName()));
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
	@Override
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
				return (T) first;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public List<Table> getTables() throws SQLException {
		String sql = " select convert(varchar(100),t.name) tableName,convert(nvarchar(200),ext.value) tableComment "
					+" from sys.tables t left join sys.extended_properties ext on ext.major_id = t.object_id and ext.name = 'MS_Description' and ext.minor_id = '0' ";
		return executeResultSet(sql, Table.class);
	}

	@Override
	public List<Column> getTableColumns(String tableName) throws SQLException {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append(" select convert(varchar(100),c.name) columnName,convert(nvarchar(200),c.comments) columnComment,convert(varchar(100),c.is_identity) columnKey,convert(varchar(100),t.name) columnType");
		sqlBuilder.append(" from ");
		sqlBuilder.append(" ( ");
		sqlBuilder.append(" select col.name,col.column_id,col.system_type_id,col.is_identity,ext.comments ");
		sqlBuilder.append(" from sys.columns col ");
		sqlBuilder.append(" left join (select minor_id,value comments from sys.extended_properties "
				+ "where major_id in (select object_id from sys.tables where name='%s') and name = 'MS_Description') ext " 
				+ "on ext.minor_id = col.column_id ");
		sqlBuilder.append(" where col.object_id in (select object_id from sys.tables where name='%s') ");
		sqlBuilder.append(" ) c left join dbo.systypes t on c.system_type_id = t.xtype");
		String sql = String.format(sqlBuilder.toString(), tableName, tableName);
		return executeResultSet(sql, Column.class);
	}
}
