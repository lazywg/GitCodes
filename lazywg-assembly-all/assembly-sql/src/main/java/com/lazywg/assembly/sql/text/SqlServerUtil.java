package com.lazywg.assembly.sql.text;

import com.lazywg.assembly.sql.entity.SQLPager;
import com.lazywg.assembly.sql.utils.SQLClassUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * author: gaowang
 *
 * createTime:2018年1月31日 上午10:59:32
 *
 */
public class SqlServerUtil extends SQLUtil {
	
	@Override
	public <T> String getSumByWhereSqlText(Class<T> cls, String fieldName, String sqlWhere) {
		String tableName = SQLClassUtil.getTableName(cls);
		return String.format("select isnull(sum(isnull(%s,0)),0) sum from %s where 1=1 %s ", fieldName, tableName, sqlWhere);
	}
	
	public <T> String getSelectByPagerSqlText(String sqlWhere, SQLPager pager, Class<T> cls) {
		String tableName = SQLClassUtil.getTableName(cls);
		List<Field> selectFields = SQLClassUtil.getTableSelectFields(cls);
		String fieldsStr = getTableFieldsString(selectFields);
		String orderStr = pager.getOrderStr();
		if (StringUtils.isBlank(orderStr)) {
			List<Field> keyFields = SQLClassUtil.getTablePrimaryKeyFields(cls);
			if (keyFields.size() < 1) {
				throw new IllegalArgumentException(String.format("no primary key for table %s!!!", tableName));
			}
			orderStr = getTableOrderFieldsString(keyFields);
		}
		if (!StringUtils.isBlank(orderStr)) {
			orderStr = String.format("order by %s", orderStr);
		}
		return String.format(
				"select %s from (select *,ROW_NUMBER() over(%s) as rownumber from %s) as t where 1=1 %s and rownumber between %d and %d",
				fieldsStr, orderStr, tableName, sqlWhere, pager.getStartIndex(), pager.getEndIndex());
	}
}