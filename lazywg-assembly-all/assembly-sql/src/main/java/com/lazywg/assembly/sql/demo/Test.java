package com.lazywg.assembly.sql.demo;


import com.lazywg.assembly.sql.config.SQLConfig;
import com.lazywg.assembly.sql.enums.SQLType;
import com.lazywg.assembly.sql.service.gen.GenUtil;

/**
 * @author: gaowang
 *
 * @createTime: 2019年1月17日 下午9:39:06
 *
 */
public class Test {

	public static void main(String[] args) {
		// sql server 1433
		SQLConfig sqlConfig = new SQLConfig("47.101.185.24", 3306, "root", "root@123456", "fund", SQLType.MYSQL);
		sqlConfig.setProxy(true);
		GenUtil entityUtil = new GenUtil("gaowang", sqlConfig, true);
		String outDir = "C://";
		System.out.println(entityUtil.genController(outDir,"com.test"));
	}
}
