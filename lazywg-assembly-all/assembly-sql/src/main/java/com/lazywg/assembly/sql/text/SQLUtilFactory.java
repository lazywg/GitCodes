package com.lazywg.assembly.sql.text;


import com.lazywg.assembly.sql.enums.SQLType;

import java.util.HashMap;
import java.util.Map;

/**
 * author: gaowang
 *
 * createTime:2018年1月31日 下午8:26:15
 *
 */
public final class SQLUtilFactory {

	private static final Object _locker = new Object();

	private static Map<SQLType, ISQLUtil> utilDict = new HashMap<SQLType, ISQLUtil>();

	private SQLUtilFactory() {
		
	}

	static {
		utilDict.put(SQLType.MYSQL, new MySqlUtil());
		utilDict.put(SQLType.SQLSERVER, new SqlServerUtil());
		utilDict.put(SQLType.ORACLE, new OracleUtil());
	}

	/**
	 * 创建SQL
	 * 
	 * @param sqlType
	 * @return
	 */
	public static ISQLUtil getSQLUtil(SQLType sqlType) {

		synchronized (_locker) {
			if (!utilDict.containsKey(sqlType)) {
				switch (sqlType) {
					case SQLSERVER :
						utilDict.put(sqlType, new SqlServerUtil());
						break;
					case ORACLE :
						utilDict.put(sqlType, new OracleUtil());
						break;
					case MYSQL :
					default :
						utilDict.put(sqlType, new MySqlUtil());
						break;
				}
			}
			return utilDict.get(sqlType);
		}
	}
}
