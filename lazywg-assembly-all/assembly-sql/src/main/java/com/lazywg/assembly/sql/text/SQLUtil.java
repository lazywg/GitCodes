package com.lazywg.assembly.sql.text;


import com.lazywg.assembly.sql.annotation.SQLField;
import com.lazywg.assembly.sql.entity.SQLPager;
import com.lazywg.assembly.sql.entity.SQLParam;
import com.lazywg.assembly.sql.enums.SQLFieldType;
import com.lazywg.assembly.sql.utils.DateFmtUtil;
import com.lazywg.assembly.sql.utils.SQLClassUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class SQLUtil implements ISQLUtil {

	@Override
	public <T> String getInsertSqlText(T t) {
		@SuppressWarnings("unchecked")
		Class<T> cls = (Class<T>) t.getClass();
		String tableName = SQLClassUtil.getTableName(cls);
		List<Field> tableFields = SQLClassUtil.getTableInsertFields(cls);
		String tableFieldsStr = getTableFieldsString(tableFields);
		String insertValStr = getInsertValuesString(t, tableFields);
		return String.format("insert into %s(%s)values(%s)", tableName, tableFieldsStr, insertValStr);
	}

	@Override
	public <T> String getBatchInsertSqlText(List<T> list) {
		StringBuilder builder = new StringBuilder();
		for (T t : list) {
			builder.append(getInsertSqlText(t));
			builder.append(";");
			builder.append("\n");
		}
		return builder.toString();
	}

	@Override
	public <T> List<String> getListInsertSqlText(List<T> list) {
		List<String> sqlStrList = new ArrayList<String>();
		for (T t : list) {
			sqlStrList.add(getInsertSqlText(t));
		}
		return sqlStrList;
	}

	@Override
	public <T> String getUpdateSqlText(T t) {
		@SuppressWarnings("unchecked")
		Class<T> cls = (Class<T>) t.getClass();
		String tableName = SQLClassUtil.getTableName(cls);
		List<Field> updateFields = SQLClassUtil.getTableUpdateFields(cls);
		List<Field> keyFields = SQLClassUtil.getTablePrimaryKeyFields(cls);
		if (keyFields.size() < 1) {
			throw new IllegalArgumentException(String.format("no primary key for table %s!!!", tableName));
		}
		String tableUpdateStr = getTableUpdateString(t, updateFields);
		String tableFieldWhere = getClassFieldWhere(t, keyFields);
		return String.format(" update %s set %s where 1=1 %s", tableName, tableUpdateStr, tableFieldWhere);
	}

	@Override
	public <T> String getBatchUpdateSqlText(List<T> list) {
		StringBuilder builder = new StringBuilder();
		for (T t : list) {
			builder.append(getUpdateSqlText(t));
			builder.append(";");
			builder.append("\n");
		}
		return builder.toString();
	}

	@Override
	public <T> List<String> getListUpdateSqlText(List<T> subList) {
		List<String> sqlStrList = new ArrayList<String>();
		for (T t : subList) {
			sqlStrList.add(getUpdateSqlText(t));
		}
		return sqlStrList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> String getUpdateByWhereSqlText(T t, String sqlWhere) {
		Class<T> cls = (Class<T>) t.getClass();
		String tableName = SQLClassUtil.getTableName(cls);
		String tableUpdateStr = getTableUpdateString(t, SQLClassUtil.getTableUpdateFields(cls));
		return String.format(" update %s set %s where 1=1 %s", tableName, tableUpdateStr, sqlWhere);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T> String getUpdateByWhereSqlText(T t, List<SQLParam> whereParams) {
		String sqlWhere = getSQLParamsWhere(whereParams);
		return getUpdateByWhereSqlText(t, sqlWhere);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T> String getUpdateByWhereSqlText(List<SQLParam> setParams, String sqlWhere, Class<T> cls) {
		String tableName = SQLClassUtil.getTableName(cls);
		String tableUpdateStr = getTableUpdateString(setParams);
		return String.format(" update %s set %s where 1=1 %s", tableName, tableUpdateStr, sqlWhere);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T> String getUpdateByWhereSqlText(List<SQLParam> setParams, List<SQLParam> whereParams, Class<T> cls) {
		String sqlWhere = getSQLParamsWhere(whereParams);
		return getUpdateByWhereSqlText(setParams, sqlWhere, cls);
	}

	@Override
	public <T> String getDeleteByWhereSqlText(String sqlWhere, Class<T> cls) {
		String tableName = SQLClassUtil.getTableName(cls);
		return String.format("delete from %s where 1=1 %s", tableName, sqlWhere);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T> String getDeleteByWhereSqlText(List<SQLParam> whereParams, Class<T> cls) {
		String sqlWhere = getSQLParamsWhere(whereParams);
		return getDeleteByWhereSqlText(sqlWhere, cls);
	}

	@Override
	public <T> String getCountSqlText(Class<T> cls) {
		return getCountByWhereSqlText(StringUtils.EMPTY, cls);
	}

	@Override
	public <T> String getCountByWhereSqlText(String sqlWhere, Class<T> cls) {
		String tableName = SQLClassUtil.getTableName(cls);
		return String.format("select count(1) count from %s where 1=1 %s ", tableName, sqlWhere);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T> String getCountByWhereSqlText(List<SQLParam> whereParams, Class<T> cls) {
		String sqlWhere = getSQLParamsWhere(whereParams);
		return getCountByWhereSqlText(sqlWhere, cls);
	}

	@Override
	public <T> String getSumByWhereSqlText(Class<T> cls, String fieldName, List<SQLParam> whereParams) {
		String sqlWhere = getSQLParamsWhere(whereParams);
		return getSumByWhereSqlText(cls, fieldName, sqlWhere);
	}

	@Override
	public <T> String getSelectSqlText(Class<T> cls) {
		String tableName = SQLClassUtil.getTableName(cls);
		List<Field> selectFields = SQLClassUtil.getTableSelectFields(cls);
		String fieldsStr = getTableFieldsString(selectFields);
		return String.format("select %s from %s", fieldsStr, tableName);
	}

	@Override
	public <T> String getSelectByWhereSqlText(String sqlWhere, Class<T> cls) {
		String tableName = SQLClassUtil.getTableName(cls);
		List<Field> selectFields = SQLClassUtil.getTableSelectFields(cls);
		String fieldsStr = getTableFieldsString(selectFields);
		return String.format("select %s from %s where 1=1 %s", fieldsStr, tableName, sqlWhere);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T> String getSelectByWhereSqlText(List<SQLParam> whereParams, Class<T> cls) {
		String sqlWhere = getSQLParamsWhere(whereParams);
		return getSelectByWhereSqlText(sqlWhere, cls);
	}

	@Override
	public <T> String getSelectByWhereSqlText(String sqlWhere, String orderWhere, Class<T> cls) {
		String tableName = SQLClassUtil.getTableName(cls);
		List<Field> selectFields = SQLClassUtil.getTableSelectFields(cls);
		String fieldsStr = getTableFieldsString(selectFields);
		return String.format("select %s from %s where 1=1 %s order by %s", fieldsStr, tableName, sqlWhere, orderWhere);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T> String getSelectByWhereSqlText(List<SQLParam> whereParams, String orderWhere, Class<T> cls) {
		String sqlWhere = getSQLParamsWhere(whereParams);
		return getSelectByWhereSqlText(sqlWhere, orderWhere, cls);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <T> String getSelectByPagerSqlText(List<SQLParam> whereParams, SQLPager pager, Class<T> cls) {
		String sqlWhere = getSQLParamsWhere(whereParams);
		return getSelectByPagerSqlText(sqlWhere, pager, cls);
	}

	@Override
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
		return String.format("select %s from %s where 1=1 %s %s limit %d,%d", fieldsStr, tableName, sqlWhere, orderStr, pager.getStartIndex() - 1, pager.getPageSize());
	}

	protected String getTableFieldsString(List<Field> tableFields) {
		StringBuilder builder = new StringBuilder();
		for (Field field : tableFields) {
			SQLField sqlField = field.getAnnotation(SQLField.class);
			if (sqlField == null || StringUtils.isBlank(sqlField.value())) {
				builder.append(String.format("%s,", field.getName()));
			} else {
				builder.append(String.format("%s,", sqlField.value()));
			}
		}
		if (builder.length() > 0) {
			return builder.substring(0, builder.length() - 1);
		}
		return StringUtils.EMPTY;
	}

	protected String getTableOrderFieldsString(List<Field> tableFields) {
		StringBuilder builder = new StringBuilder();
		for (Field field : tableFields) {
			SQLField sqlField = field.getAnnotation(SQLField.class);
			if (sqlField == null || StringUtils.isBlank(sqlField.value())) {
				builder.append(String.format("%s asc,", field.getName()));
			} else {
				builder.append(String.format("%s asc,", sqlField.value()));
			}
		}
		if (builder.length() > 0) {
			return builder.substring(0, builder.length() - 1);
		}
		return StringUtils.EMPTY;
	}

	protected <T> String getInsertValuesString(T t, List<Field> tableFields) {
		StringBuilder builder = new StringBuilder();
		Method[] methods = SQLClassUtil.getEntityMethods(t.getClass());

		for (Field field : tableFields) {
			SQLFieldType sqlFieldType = SQLClassUtil.getSQLFieldType(field);
			Object val = null;
			try {
				Method method = SQLClassUtil.getFieldGetMethod(methods, field);
				val = method.invoke(t, null);
				if (val != null && sqlFieldType == SQLFieldType.DATETIME) {
					val = DateFmtUtil.dateFormate((Date) val);
				}
				if (val != null && sqlFieldType == SQLFieldType.BOOL) {
					val = Boolean2Bit((boolean) val);
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
			if (val != null && judgeNeedSingleQuote(sqlFieldType)) {
				builder.append(String.format("'%s',", val));
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
			try {
				Method method = SQLClassUtil.getFieldGetMethod(methods, field);
				val = method.invoke(t, null);
				if (val != null && sqlFieldType == SQLFieldType.DATETIME) {
					val = DateFmtUtil.dateFormate((Date) val);
				}
				if (val != null && sqlFieldType == SQLFieldType.BOOL) {
					val = Boolean2Bit((boolean) val);
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
			if (val != null && judgeNeedSingleQuote(sqlFieldType)) {
				builder.append(String.format("%s='%s',", SQLClassUtil.getSQLFieldName(field), val));
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
			}
			if (val != null && sqlFieldType == SQLFieldType.BOOL) {
				val = Boolean2Bit((boolean) val);
			}
			if (val != null && judgeNeedSingleQuote(sqlFieldType)) {
				builder.append(String.format("%s='%s',", sqlParam.getFieldName(), val));
			} else {
				builder.append(String.format("%s=%s,", sqlParam.getFieldName(), val));
			}
		}
		if (builder.length() > 0) {
			return builder.substring(0, builder.length() - 1);
		}
		return StringUtils.EMPTY;
	}

	protected <T> String getClassFieldWhere(T t, List<Field> keyFields) {
		StringBuilder builder = new StringBuilder();
		Method[] methods = SQLClassUtil.getEntityMethods(t.getClass());
		for (Field field : keyFields) {
			SQLFieldType sqlFieldType = SQLClassUtil.getSQLFieldType(field);
			Object val = null;
			try {
				Method method = SQLClassUtil.getFieldGetMethod(methods, field);
				val = method.invoke(t, null);
				if (val != null && sqlFieldType == SQLFieldType.DATETIME) {
					val = DateFmtUtil.dateFormate((Date) val);
				}
				if (val != null && sqlFieldType == SQLFieldType.BOOL) {
					val = Boolean2Bit((boolean) val);
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
			if (val != null && judgeNeedSingleQuote(sqlFieldType)) {
				builder.append(String.format(" and %s='%s'", SQLClassUtil.getSQLFieldName(field), val));
			} else {
				builder.append(String.format(" and %s=%s", SQLClassUtil.getSQLFieldName(field), val));
			}
		}
		return builder.toString();
	}

	@SuppressWarnings("rawtypes")
	protected <T> String getSQLParamsWhere(List<SQLParam> whereParams) {
		StringBuilder builder = new StringBuilder();
		for (SQLParam sqlParam : whereParams) {
			SQLFieldType sqlFieldType = sqlParam.getFieldType();
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
			}
			if (val != null && sqlFieldType == SQLFieldType.BOOL) {
				val = Boolean2Bit((boolean) val);
			}
			if (val != null && judgeNeedSingleQuote(sqlFieldType)) {
				builder.append(String.format(" and %s='%s'", sqlParam.getFieldName(), sqlParam.getFieldValue()));
			} else {
				builder.append(String.format(" and %s=%s", sqlParam.getFieldName(), sqlParam.getFieldValue()));
			}
		}
		return builder.toString();
	}

	protected boolean judgeNeedSingleQuote(SQLFieldType sqlFieldType) {
		if (sqlFieldType == SQLFieldType.NONE || sqlFieldType == SQLFieldType.DATETIME || sqlFieldType == SQLFieldType.GUID || sqlFieldType == SQLFieldType.STRING) {
			return true;
		}
		return false;
	}

	protected int Boolean2Bit(boolean bool) {
		return bool ? 1 : 0;
	}
}
