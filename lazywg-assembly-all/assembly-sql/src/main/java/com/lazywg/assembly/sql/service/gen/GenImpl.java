package com.lazywg.assembly.sql.service.gen;


import com.lazywg.assembly.sql.config.SQLConfig;
import com.lazywg.assembly.sql.service.BaseSQLImpl;

/**
 * @author: gaowang
 *
 * @createTime: 2019年1月18日 上午10:15:10
 *
 */
public class GenImpl extends BaseSQLImpl {

	private SQLConfig sqlConfig;

	public GenImpl(SQLConfig config) {
		this.sqlConfig = config;
	}

	@Override
	public SQLConfig getSQLConfig() {
		return sqlConfig;
	}
}
