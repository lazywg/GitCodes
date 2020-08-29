package com.lazywg.assembly.sql.text;

import com.lazywg.assembly.sql.utils.SQLClassUtil;

/**
 * author: gaowang
 *
 * createTime:2018年1月31日 上午10:59:04
 *
 */
public class MySqlUtil extends SQLUtil {
	
	@Override
	public <T> String getSumByWhereSqlText(Class<T> cls, String fieldName, String sqlWhere) {
		String tableName = SQLClassUtil.getTableName(cls);
		return String.format("select coalesce(sum(coalesce(%s,0)),0) sum from %s where 1=1 %s ", fieldName, tableName, sqlWhere);
	}
}
