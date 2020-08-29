package com.lazywg.assembly.sql.text;


import com.lazywg.assembly.sql.entity.SQLPager;
import com.lazywg.assembly.sql.entity.SQLParam;
import com.lazywg.assembly.sql.enums.SQLFieldType;
import com.lazywg.assembly.sql.utils.DateFmtUtil;
import com.lazywg.assembly.sql.utils.SQLClassUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

/**
 * author: gaowang
 *
 * createTime:2018年1月31日 上午10:59:20
 *
 */
public class OracleUtil extends SQLUtil {
	
	@Override
	public <T> String getSumByWhereSqlText(Class<T> cls, String fieldName, String sqlWhere) {
		String tableName = SQLClassUtil.getTableName(cls);
		return String.format("select nvl(sum(nvl(%s,0)),0) sum from %s where 1=1 %s ", fieldName, tableName, sqlWhere);
	}
	
	protected <T> String getInsertValuesString(T t, List<Field> tableFields) {
		StringBuilder builder = new StringBuilder();
		Method[] methods = SQLClassUtil.getEntityMethods(t.getClass());

		for (Field field : tableFields) {
			boolean flag = false;
			SQLFieldType sqlFieldType = SQLClassUtil.getSQLFieldType(field);
			Object val = null;
			try {
				Method method = SQLClassUtil.getFieldGetMethod(methods, field);
				val = method.invoke(t, null);

				if (val != null && sqlFieldType == SQLFieldType.DATETIME) {
					val = DateFmtUtil.dateFormate((Date) val);
					flag = true;
				}
				if (val != null && sqlFieldType == SQLFieldType.BOOL) {
					val = Boolean2Bit((boolean) val);
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
			if (val != null && judgeNeedSingleQuote(sqlFieldType)) {
				if (flag) {
					builder.append(String.format("TO_DATE('%s','SYYYY-MM-DD HH24:MI:SS'),", val));
				} else {
					builder.append(String.format("'%s',", val));
				}
			} else {
				builder.append(String.format("%s,", val));
			}
		}
		if (builder.length() > 0) {
			return builder.substring(0, builder.length() - 1);
		}
		return StringUtils.EMPTY;
	}
	

	protected <T> String getTableUpdateString(T t, List<Field> updateFields) {
		StringBuilder builder = new StringBuilder();
		Method[] methods = SQLClassUtil.getEntityMethods(t.getClass());
		for (Field field : updateFields) {
			SQLFieldType sqlFieldType = SQLClassUtil.getSQLFieldType(field);
			Object val = null;
			boolean flag = false;
			try {
				Method method = SQLClassUtil.getFieldGetMethod(methods, field);
				val = method.invoke(t, null);
				if (val != null && sqlFieldType == SQLFieldType.DATETIME) {
					val = DateFmtUtil.dateFormate((Date) val);
					flag = true;
				}
				if (val != null && sqlFieldType == SQLFieldType.BOOL) {
					val = Boolean2Bit((boolean) val);
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
			if (val != null && judgeNeedSingleQuote(sqlFieldType)) {
				if (flag) {
					builder.append(String.format("%s=TO_DATE('%s','SYYYY-MM-DD HH24:MI:SS'),", SQLClassUtil.getSQLFieldName(field), val));
				} else {
					builder.append(String.format("%s='%s',", SQLClassUtil.getSQLFieldName(field), val));
				}				
			} else {
				builder.append(String.format("%s=%s,", SQLClassUtil.getSQLFieldName(field), val));
			}
		}
		if (builder.length() > 0) {
			return builder.substring(0, builder.length() - 1);
		}
		return StringUtils.EMPTY;
	}

	@SuppressWarnings("rawtypes")
	protected String getTableUpdateString(List<SQLParam> updateParams) {
		StringBuilder builder = new StringBuilder();
		for (SQLParam sqlParam : updateParams) {
			SQLFieldType sqlFieldType = sqlParam.getFieldType();
			boolean flag = false;
			if (sqlFieldType == null || sqlFieldType == SQLFieldType.NONE) {
				try {
					sqlFieldType = SQLFieldType.getSQLFieldType(sqlParam.getClass().getField("fieldValue").getType());
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
			Object val = sqlParam.getFieldValue();
			if (val != null && sqlFieldType == SQLFieldType.DATETIME) {
				val = DateFmtUtil.dateFormate((Date) val);
				flag = true;
			}
			if (val != null && sqlFieldType == SQLFieldType.BOOL) {
				val = Boolean2Bit((boolean) val);
			}
			if (val != null && judgeNeedSingleQuote(sqlFieldType)) {
				if (flag) {
					builder.append(String.format("%s=TO_DATE('%s','SYYYY-MM-DD HH24:MI:SS'),", sqlParam.getFieldName(), val));
				} else {
					builder.append(String.format("%s='%s',",sqlParam.getFieldName(), val));
				}
			} else {
				builder.append(String.format("%s=%s,", sqlParam.getFieldName(), val));
			}
		}
		if (builder.length() > 0) {
			return builder.substring(0, builder.length() - 1);
		}
		return StringUtils.EMPTY;
	}
	//因主键ID都是UUID排序，故删除由自定义排序。
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
		return String.format("select %s from (select rownum rn,u.* from (select t.* from %s t where 1 = 1 %s %s) u) ts where ts.rn between %d and %d",fieldsStr, tableName, sqlWhere,orderStr, pager.getStartIndex(), pager.getEndIndex());
	}
}
