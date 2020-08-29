package com.lazywg.assembly.sql.factory;


import com.lazywg.assembly.sql.ISQL;
import com.lazywg.assembly.sql.MySql;
import com.lazywg.assembly.sql.Oracle;
import com.lazywg.assembly.sql.SqlServer;
import com.lazywg.assembly.sql.config.SQLConfig;

/**
 * SQL工厂 单例实现
 * 
 * @author gaowang
 *
 */

// 需要池化
public class SQLFactory implements AutoCloseable{

	private static final Object _locker = new Object();

	private static SQLFactory _instance = null;
	
	private SQLFactory() {
	}

	public static SQLFactory getInstance() {
		if (null == _instance) {
			synchronized (_locker) {
				if (null == _instance) {
					_instance = new SQLFactory();
				}
			}
		}
		return _instance;
	}

	/**
	 * 创建SQL
	 * 
	 * @param config
	 * @return
	 */
	public ISQL createSQL(SQLConfig config) {
		switch (config.getSqlType()) {
			case SQLSERVER :
				return new SqlServer(config);
			case ORACLE :
				return new Oracle(config);
			case MYSQL :
			default :
				return new MySql(config);
		}
	}

	@Override
	public void close() throws Exception {

	}
}
