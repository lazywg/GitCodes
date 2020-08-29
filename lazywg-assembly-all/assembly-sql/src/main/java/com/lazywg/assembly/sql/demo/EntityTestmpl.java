package com.lazywg.assembly.sql.demo;


import com.lazywg.assembly.sql.config.SQLConfig;
import com.lazywg.assembly.sql.enums.SQLType;
import com.lazywg.assembly.sql.service.BaseSQLImpl;

/**
 * author: gaowang
 *
 * createTime:2018年2月1日 下午8:28:26
 *
 */
public class EntityTestmpl extends BaseSQLImpl implements IEntityTest {

	@Override
	public SQLConfig getSQLConfig() {
		//return new SQLConfig("10.1.180.223", 1433, "sa", "123456", "make_platform", SQLType.SQLSERVER);
		//return new SQLConfig("10.1.180.223", 3306, "root", "iflytek", "make_platform", SQLType.MYSQL);
		SQLConfig sqlConfig = new SQLConfig("47.101.185.24", 3306, "root", "root@123456", "fund", SQLType.MYSQL);
		sqlConfig.setProxy(true);
		return sqlConfig;
	}
}
