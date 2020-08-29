package com.lazywg.assembly.sql.service;


import com.lazywg.assembly.sql.ISQL;
import com.lazywg.assembly.sql.SQLTruncate;
import com.lazywg.assembly.sql.config.SQLConfig;
import com.lazywg.assembly.sql.entity.Column;
import com.lazywg.assembly.sql.entity.SQLPager;
import com.lazywg.assembly.sql.entity.SQLParam;
import com.lazywg.assembly.sql.entity.Table;
import com.lazywg.assembly.sql.factory.SQLFactory;
import com.lazywg.assembly.sql.factory.SQLProxyFactory;
import com.lazywg.assembly.sql.text.ISQLUtil;
import com.lazywg.assembly.sql.text.SQLUtilFactory;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

/**
 * author: gaowang
 *
 * createTime:2018年1月31日 下午1:46:43
 *
 */
public abstract class BaseSQLImpl implements IBaseSQL {

	private static Logger logger = Logger.getLogger(BaseSQLImpl.class);

	@Override
	public <T> boolean insert(T t) {
		String sqlStr = getSQLUtil().getInsertSqlText(t);
		try {
			logger.info(String.format("%s|%s", "insert", sqlStr));
			return getProxySQL().executeNonQuery(sqlStr) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "insert"), e);
		}
		return false;
	}

	/**
	 * 事务操作具有原子性 需要用到真实SQL 并手动关闭
	 */
	@Override
	public <T> boolean batchInsert(List<T> list) {

		if (list == null || list.size() < 1) {
			return false;
		}

		ISQL sql = null;
		SQLTruncate tran = null;
		try {
			logger.info(String.format("%s|%s", "batchInsert", list.size()));
			sql = getSQL();
			tran = sql.createTruncate();
			tran.begin();

			SQLPager pager = new SQLPager(0, sql.getBatchSize());
			pager.setDataCount(list.size());
			for (int i = 0; i < pager.getPageCount(); i++) {
				pager.setPageIndex(i + 1);
				List<T> subList = list.subList(pager.getStartIndex() - 1, pager.getEndIndex());
				List<String> sqlStrs = getSQLUtil().getListInsertSqlText(subList);
				sql.executeBatch(sqlStrs, tran);
			}
			tran.commit();
			return true;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "batchInsert"), e);
			if (tran != null) {
				tran.rollback();
			}
		} finally {
			try {
				if (tran != null) {
					tran.close();
				}
				if (sql != null) {
					sql.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public <T> boolean update(T t) {
		String sqlStr = getSQLUtil().getUpdateSqlText(t);
		try {
			logger.info(String.format("%s|%s", "update", sqlStr));
			return getProxySQL().executeNonQuery(sqlStr) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "update"), e);
		}
		return false;
	}

	/**
	 * 事务操作具有原子性 需要用到真实SQL 并手动关闭
	 */
	@Override
	public <T> boolean batchUpdate(List<T> list) {
		if (list == null || list.size() < 1) {
			return false;
		}
		SQLTruncate tran = null;
		ISQL sql = null;
		try {
			logger.info(String.format("%s|%s", "batchUpdate", list.size()));
			sql = getSQL();
			tran = sql.createTruncate();
			tran.begin();

			SQLPager pager = new SQLPager(0, sql.getBatchSize());
			pager.setDataCount(list.size());
			for (int i = 0; i < pager.getPageCount(); i++) {
				pager.setPageIndex(i + 1);
				List<T> subList = list.subList(pager.getStartIndex() - 1, pager.getEndIndex());
				List<String> sqlStrs = getSQLUtil().getListUpdateSqlText(subList);
				sql.executeBatch(sqlStrs, tran);
			}
			tran.commit();
			return true;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			if (tran != null) {
				tran.rollback();
			}
			logger.error(String.format("%s", "batchUpdate"), e);
		} finally {
			try {
				if (tran != null) {
					tran.close();
				}
				if (sql != null) {
					sql.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T> boolean updateByWhere(List<SQLParam> setParams, String sqlWhere, Class<T> cls) {
		String sqlStr = getSQLUtil().getUpdateByWhereSqlText(setParams, sqlWhere, cls);
		try {
			logger.info(String.format("%s|%s", "updateByWhere", sqlStr));
			return getProxySQL().executeNonQuery(sqlStr) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "updateByWhere"), e);
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T> boolean updateByWhere(List<SQLParam> setParams, List<SQLParam> whereParams, Class<T> cls) {
		String sqlStr = getSQLUtil().getUpdateByWhereSqlText(setParams, whereParams, cls);
		try {
			logger.info(String.format("%s|%s", "updateByWhere", sqlStr));
			return getProxySQL().executeNonQuery(sqlStr) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "updateByWhere"), e);
		}
		return false;
	}

	@Override
	public <T> boolean updateByWhere(T t, String sqlWhere) {
		String sqlStr = getSQLUtil().getUpdateByWhereSqlText(t, sqlWhere);
		try {
			logger.info(String.format("%s|%s", "updateByWhere", sqlStr));
			return getProxySQL().executeNonQuery(sqlStr) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "updateByWhere"), e);
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T> boolean updateByWhere(T t, List<SQLParam> whereParams) {
		String sqlStr = getSQLUtil().getUpdateByWhereSqlText(t, whereParams);
		try {
			logger.info(String.format("%s|%s", "updateByWhere", sqlStr));
			return getProxySQL().executeNonQuery(sqlStr) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "updateByWhere"), e);
		}
		return false;
	}

	@Override
	public <T> long count(Class<T> cls) {
		String sqlStr = getSQLUtil().getCountSqlText(cls);
		try {
			logger.info(String.format("%s|%s", "count", sqlStr));
			return getProxySQL().executeScalar(sqlStr);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "count"), e);
		}
		return 0;
	}

	@Override
	public <T> long countByWhere(String sqlWhere, Class<T> cls) {
		String sqlStr = getSQLUtil().getCountByWhereSqlText(sqlWhere, cls);
		try {
			logger.info(String.format("%s|%s", "countByWhere", sqlStr));
			return getProxySQL().executeScalar(sqlStr);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "countByWhere"), e);
		}
		return 0;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T> long countByWhere(List<SQLParam> whereParams, Class<T> cls) {
		String sqlStr = getSQLUtil().getCountByWhereSqlText(whereParams, cls);
		try {
			logger.info(String.format("%s|%s", "countByWhere", sqlStr));
			return getProxySQL().executeScalar(sqlStr);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "countByWhere"), e);
		}
		return 0;
	}

	/**
	 * 和
	 * 
	 * @param sqlWhere
	 * @param cls
	 * @return
	 */
	public <T> double sumByWhere(String sqlWhere, String fieldName, Class<T> cls) {
		String sqlStr = getSQLUtil().getSumByWhereSqlText(cls, fieldName, sqlWhere);
		try {
			logger.info(String.format("%s|%s", "sumByWhere", sqlStr));
			return getProxySQL().executeScalar(sqlStr, double.class).doubleValue();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "sumByWhere"), e);
		}
		return 0;
	}

	/**
	 * 和
	 * 
	 * @param whereParams
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public <T> double sumByWhere(List<SQLParam> whereParams, String fieldName, Class<T> cls) {
		String sqlStr = getSQLUtil().getSumByWhereSqlText(cls, fieldName, whereParams);
		try {
			logger.info(String.format("%s|%s", "sumByWhere", sqlStr));
			return getProxySQL().executeScalar(sqlStr, double.class);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "sumByWhere"), e);
		}
		return 0;
	}

	@Override
	public <T> boolean existByWhere(String sqlWhere, Class<T> cls) {
		return countByWhere(sqlWhere, cls) > 0;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T> boolean existByWhere(List<SQLParam> whereParams, Class<T> cls) {
		return countByWhere(whereParams, cls) > 0;
	}

	@Override
	public <T> boolean deleteByWhere(String sqlWhere, Class<T> cls) {
		String sqlStr = getSQLUtil().getDeleteByWhereSqlText(sqlWhere, cls);
		try {
			logger.info(String.format("%s|%s", "deleteByWhere", sqlStr));
			return getProxySQL().executeNonQuery(sqlStr) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "deleteByWhere"), e);
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T> boolean deleteByWhere(List<SQLParam> whereParams, Class<T> cls) {
		String sqlStr = getSQLUtil().getDeleteByWhereSqlText(whereParams, cls);
		try {
			logger.info(String.format("%s|%s", "deleteByWhere", sqlStr));
			return getProxySQL().executeNonQuery(sqlStr) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "deleteByWhere"), e);
		}
		return true;
	}

	@Override
	public <T> List<T> select(Class<T> cls) {
		String sqlStr = getSQLUtil().getSelectSqlText(cls);
		try {
			logger.info(String.format("%s|%s", "select", sqlStr));
			return getProxySQL().executeResultSet(sqlStr, cls);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "select"), e);
		}
		return null;
	}

	@Override
	public <T> List<T> selectByWhere(String sqlWhere, Class<T> cls) {
		String sqlStr = getSQLUtil().getSelectByWhereSqlText(sqlWhere, cls);
		try {
			logger.info(String.format("%s|%s", "selectByWhere", sqlStr));
			return getProxySQL().executeResultSet(sqlStr, cls);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "selectByWhere"), e);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T> List<T> selectByWhere(List<SQLParam> whereParams, Class<T> cls) {
		String sqlStr = getSQLUtil().getSelectByWhereSqlText(whereParams, cls);
		try {
			logger.info(String.format("%s|%s", "selectByWhere", sqlStr));
			return getProxySQL().executeResultSet(sqlStr, cls);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "selectByWhere"), e);
		}
		return null;
	}

	@Override
	public <T> List<T> selectByWhere(String sqlWhere, String orderWhere, Class<T> cls) {
		String sqlStr = getSQLUtil().getSelectByWhereSqlText(sqlWhere, orderWhere, cls);
		try {
			logger.info(String.format("%s|%s", "selectByWhere", sqlStr));
			return getProxySQL().executeResultSet(sqlStr, cls);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "selectByWhere"), e);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T> T selectFirst(List<SQLParam> whereParams, Class<T> cls) {
		List<T> ret = selectByWhere(whereParams, cls);
		if (ret == null || ret.size() < 1) {
			return null;
		}
		return ret.get(0);
	}

	@Override
	public <T> T selectFirst(String sqlWhere, Class<T> cls) {
		List<T> ret = selectByWhere(sqlWhere, cls);
		if (ret == null || ret.size() < 1) {
			return null;
		}
		return ret.get(0);
	}

	@Override
	public <T> List<T> selectByWhere(List<SQLParam> whereParams, String orderWhere, Class<T> cls) {
		String sqlStr = getSQLUtil().getSelectByWhereSqlText(whereParams, orderWhere, cls);
		try {
			logger.info(String.format("%s|%s", "selectByWhere", sqlStr));
			return getProxySQL().executeResultSet(sqlStr, cls);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "selectByWhere"), e);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T> List<T> selectByPager(List<SQLParam> whereParams, SQLPager pager, Class<T> cls) {

		try {
			String countSqlStr = getSQLUtil().getCountByWhereSqlText(whereParams, cls);
			logger.info(String.format("%s|%s", "selectByPager", countSqlStr));
			String sqlStr = getSQLUtil().getSelectByPagerSqlText(whereParams, pager, cls);
			logger.info(String.format("%s|%s", "selectByPager", sqlStr));
			int count = (int) getProxySQL().executeScalar(countSqlStr);
			pager.setDataCount(count);
			return getProxySQL().executeResultSet(sqlStr, cls);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "selectByPager"), e);
		}
		return null;
	}

	@Override
	public <T> List<T> selectByPager(String sqlWhere, SQLPager pager, Class<T> cls) {
		try {
			String countSqlStr = getSQLUtil().getCountByWhereSqlText(sqlWhere, cls);
			logger.info(String.format("%s|%s", "selectByPager", countSqlStr));
			String sqlStr = getSQLUtil().getSelectByPagerSqlText(sqlWhere, pager, cls);
			logger.info(String.format("%s|%s", "selectByPager", sqlStr));
			int count = (int) getProxySQL().executeScalar(countSqlStr);
			pager.setDataCount(count);
			return getProxySQL().executeResultSet(sqlStr, cls);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "selectByPager"), e);
		}
		return null;
	}

	/**
	 * 代理
	 */
	@Override
	public ISQL getProxySQL() {
		return SQLProxyFactory.createSQLProxy(getSQLConfig());
	}

	/**
	 * 需要手动关闭
	 */
	@Override
	public ISQL getSQL() {
		return SQLFactory.getInstance().createSQL(getSQLConfig());
	}

	@Override
	public ISQLUtil getSQLUtil() {
		SQLConfig sqlConfig = getSQLConfig();
		return SQLUtilFactory.getSQLUtil(sqlConfig.getSqlType());
	}

	@Override
	public <T> List<T> executeResultSet(String sqlStr, Class<T> cls) {
		try {
			logger.info(String.format("%s|%s", "executeResultSet", sqlStr));
			return getProxySQL().executeResultSet(sqlStr, cls);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "executeResultSet"), e);
		}
		return null;
	}

	@Override
	public int executeNonQuery(String sql) {
		try {
			logger.info(String.format("%s|%s", "executeNonQuery", sql));
			return getProxySQL().executeNonQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "executeNonQuery"), e);
		}
		return 0;
	}

	@Override
	public List<Table> getTables() {
		try {
			logger.info(String.format("%s", "getTables"));
			return getProxySQL().getTables();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "getTables"), e);
		}
		return null;
	}

	@Override
	public List<Column> getColumns(String tableName) {
		try {
			logger.info(String.format("%s|%s", "getTables", tableName));
			return getProxySQL().getTableColumns(tableName);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(String.format("%s", "getColumns"), e);
		}
		return null;
	}
}
