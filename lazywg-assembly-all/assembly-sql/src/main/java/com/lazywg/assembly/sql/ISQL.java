package com.lazywg.assembly.sql;


import com.lazywg.assembly.sql.entity.Column;
import com.lazywg.assembly.sql.entity.Table;

import java.sql.SQLException;
import java.util.List;

/**
 * SQL 执行契约
 * 
 * @author gaowang
 *
 */
public interface ISQL extends AutoCloseable {

	/**
	 * 连接是否关闭
	 * 
	 * @return
	 * @throws SQLException
	 */
	boolean isClosed();

	/**
	 * 打开连接
	 * 
	 * @return
	 * @throws SQLException
	 */
	boolean openConnect();

	/**
	 * 获取连接
	 * 
	 * @return
	 * @throws SQLException
	 */
	// Connection getConnection() throws SQLException;

	/**
	 * 批量一次插入最大数据量
	 * 
	 * @return
	 */
	int getBatchSize();

	/**
	 * 执行结果返回
	 * 
	 * @param sqlStr
	 * @param cls
	 * @return
	 * @throws SQLException
	 */
	<T> List<T> executeResultSet(String sqlStr, Class<T> cls) throws SQLException;

	/**
	 * 执行返回受影响行数
	 * 
	 * @param sqlStr
	 * @return
	 * @throws SQLException
	 */
	int executeNonQuery(String sqlStr) throws SQLException;

	/**
	 * 返回第一行第一列
	 * 
	 * @param sqlStr
	 * @param cls
	 * @return
	 * @throws SQLException
	 */
	<T> T executeScalar(String sqlStr, Class<T> cls) throws SQLException;

	/**
	 * 返回第一行 第一列 int
	 * 
	 * @param sqlStr
	 * @return
	 * @throws SQLException
	 */
	long executeScalar(String sqlStr) throws SQLException;

	/**
	 * 预处理 用在事物连续处理
	 * 
	 * @param sqlStr
	 * @param tran
	 * @return
	 * @throws SQLException
	 */
	int executeNonQuery(String sqlStr, SQLTruncate tran) throws SQLException;

	/**
	 * 批量操作
	 * 
	 * @param sqlStrs
	 * @return
	 * @throws SQLException
	 */
	int executeBatch(List<String> sqlStrs, SQLTruncate tran) throws SQLException;

	/**
	 * 创建事务
	 * 
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	SQLTruncate createTruncate() throws ClassNotFoundException, SQLException;

	/**
	 * 获取所有表
	 * 
	 * @return
	 */
	List<Table> getTables() throws SQLException;

	/**
	 * 获取所有列
	 * 
	 * @param tableName
	 * @return
	 */
	List<Column> getTableColumns(String tableName) throws SQLException;
}
