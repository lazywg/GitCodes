package com.lazywg.assembly.sql.factory;


import com.lazywg.assembly.sql.config.SQLConfig;
import com.lazywg.assembly.sql.pool.SQLProxy;

/**
 * author: gaowang
 *
 * createTime:2018年5月15日 下午5:47:44
 *
 */
public class SQLProxyFactory {

	private static final Object _locker = new Object();

	private static SQLProxy proxy = null;

	private SQLProxyFactory() {
		
	}
	
	public static SQLProxy createSQLProxy(SQLConfig sqlConfig) {
		if (proxy == null) {
			synchronized (_locker) {
				if (proxy == null) {
					proxy = new SQLProxy(sqlConfig);
				}
			}
		}
		return proxy;
	}
}
