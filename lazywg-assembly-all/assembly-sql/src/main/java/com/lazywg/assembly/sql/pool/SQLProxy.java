package com.lazywg.assembly.sql.pool;



import com.lazywg.assembly.sql.ISQL;
import com.lazywg.assembly.sql.SQLTruncate;
import com.lazywg.assembly.sql.config.SQLConfig;
import com.lazywg.assembly.sql.entity.Column;
import com.lazywg.assembly.sql.entity.Table;
import com.lazywg.assembly.sql.factory.SQLFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * author: gaowang
 *
 * createTime:2018年5月15日 下午5:23:23
 *
 */
public class SQLProxy implements ISQL {

	private SQLPool<ISQL> sqlPool = null;
	
	public SQLProxy(SQLConfig sqlConfig){
		this.sqlPool = new SQLPool<ISQL>(sqlConfig.getPoolSize());
		for (int i = 0; i < sqlConfig.getPoolSize(); i++) {
			ISQL sqlImpl = SQLFactory.getInstance().createSQL(sqlConfig);
			try {
				sqlPool.addObject(new PoolSQL<ISQL>(sqlImpl));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	@Override
	public void close() throws Exception {
		if (sqlPool != null && sqlPool.getSize() > 0) {
			sqlPool.close();
		}
	}

	@Override
	public boolean isClosed() {
		ISQL sqlImpl = sqlPool.borrowObject();
		if (sqlImpl != null) {
			boolean ret = sqlImpl.isClosed();
			sqlPool.returnObject(sqlImpl);
			return ret;
		}
		return true;
	}

	@Override
	public boolean openConnect() {
		ISQL sqlImpl = sqlPool.borrowObject();
		if (sqlImpl != null) {
			boolean ret = sqlImpl.openConnect();
			sqlPool.returnObject(sqlImpl);
			return ret;
		}
		return false;
	}

	@Override
	public int getBatchSize() {
		ISQL sqlImpl = sqlPool.borrowObject();
		if (sqlImpl != null) {
			int ret = sqlImpl.getBatchSize();
			sqlPool.returnObject(sqlImpl);
			return ret;
		}
		return 0;
	}

	@Override
	public <T> List<T> executeResultSet(String sqlStr, Class<T> cls) throws SQLException {
		ISQL sqlImpl = sqlPool.borrowObject();
		if (sqlImpl != null) {
			List<T> ret = sqlImpl.executeResultSet(sqlStr,cls);
			sqlPool.returnObject(sqlImpl);
			return ret;
		}
		return null;
	}

	@Override
	public int executeNonQuery(String sqlStr) throws SQLException {
		ISQL sqlImpl = sqlPool.borrowObject();
		if (sqlImpl != null) {
			int ret = sqlImpl.executeNonQuery(sqlStr);
			sqlPool.returnObject(sqlImpl);
			return ret;
		}
		return 0;
	}

	@Override
	public <T> T executeScalar(String sqlStr, Class<T> cls) throws SQLException {
		ISQL sqlImpl = sqlPool.borrowObject();
		if (sqlImpl != null) {
			T ret = sqlImpl.executeScalar(sqlStr,cls);
			sqlPool.returnObject(sqlImpl);
			return ret;
		}
		return null;
	}

	@Override
	public long executeScalar(String sqlStr) throws SQLException {
		ISQL sqlImpl = sqlPool.borrowObject();
		if (sqlImpl != null) {
			long ret = sqlImpl.executeScalar(sqlStr);
			sqlPool.returnObject(sqlImpl);
			return ret;
		}
		return 0;
	}

	@Override
	public int executeNonQuery(String sqlStr, SQLTruncate tran) throws SQLException {
		ISQL sqlImpl = sqlPool.borrowObject();
		if (sqlImpl != null) {
			int ret = sqlImpl.executeNonQuery(sqlStr,tran);
			sqlPool.returnObject(sqlImpl);
			return ret;
		}
		return 0;
	}

	@Override
	public int executeBatch(List<String> sqlStrs, SQLTruncate tran) throws SQLException {
		ISQL sqlImpl = sqlPool.borrowObject();
		if (sqlImpl != null) {
			int ret = sqlImpl.executeBatch(sqlStrs,tran);
			sqlPool.returnObject(sqlImpl);
			return ret;
		}
		return 0;
	}

	@Override
	public SQLTruncate createTruncate() throws ClassNotFoundException, SQLException {
		ISQL sqlImpl = sqlPool.borrowObject();
		if (sqlImpl != null) {
			SQLTruncate ret = sqlImpl.createTruncate();
			sqlPool.returnObject(sqlImpl);
			return ret;
		}
		return null;
	}

	@Override
	public List<Table> getTables() throws SQLException {
		ISQL sqlImpl = sqlPool.borrowObject();
		if (sqlImpl != null) {
			List<Table> ret = sqlImpl.getTables();
			sqlPool.returnObject(sqlImpl);
			return ret;
		}
		return null;
	}

	@Override
	public List<Column> getTableColumns(String tableName) throws SQLException {
		ISQL sqlImpl = sqlPool.borrowObject();
		if (sqlImpl != null) {
			List<Column> ret = sqlImpl.getTableColumns(tableName);
			sqlPool.returnObject(sqlImpl);
			return ret;
		}
		return null;
	}
}
