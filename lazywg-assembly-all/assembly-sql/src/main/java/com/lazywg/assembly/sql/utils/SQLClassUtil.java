package com.lazywg.assembly.sql.utils;

import com.lazywg.assembly.sql.annotation.NoSQLField;
import com.lazywg.assembly.sql.annotation.SQLField;
import com.lazywg.assembly.sql.annotation.SQLKeyField;
import com.lazywg.assembly.sql.annotation.SQLTable;
import com.lazywg.assembly.sql.enums.SQLFieldSupportCmdType;
import com.lazywg.assembly.sql.enums.SQLFieldType;
import com.lazywg.assembly.sql.enums.SQLKeyFieldType;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * author: gaowang
 *
 * createTime:2018年2月1日 下午8:58:16
 *
 */
public class SQLClassUtil {

	/**
	 * 获取实体方法
	 * 
	 * @param cls
	 * @return
	 */
	public static <T> Method[] getEntityMethods(Class<T> cls) {
		return cls.getMethods();
	}

	/**
	 * 获取字段的 set 方法
	 * 
	 * @param methods
	 *            方法列表
	 * @param field
	 *            字段
	 * @return
	 */
	public static <T> Method getFieldSetMethod(Method[] methods, Field field) {
		for (Method method : methods) {
			if (method.getName().startsWith("set")) {
				String fieldName = field.getName();
				if (fieldName.startsWith("is")) {
					fieldName.substring(2);
				}
				if (method.getName().substring(3).equalsIgnoreCase(field.getName()) || method.getName().substring(3).equalsIgnoreCase(fieldName)) {
					return method;
				}
			}
		}
		return null;
	}

	/**
	 * 获取字段的 set 方法
	 * 
	 * @param methods
	 *            方法列表
	 * @param field
	 *            字段
	 * @return
	 */
	public static <T> Method getFieldGetMethod(Method[] methods, Field field) {
		for (Method method : methods) {
			if (method.getName().equalsIgnoreCase(field.getName())) {
				return method;
			}
			if (method.getName().startsWith("get")) {
				if (method.getName().substring(3).equalsIgnoreCase(field.getName())) {
					return method;
				}
			}
		}
		return null;
	}

	/**
	 * 获取字段的表字段名称
	 * 
	 * @param field
	 * @return
	 */
	public static String getSQLFieldName(Field field) {
		SQLField sqlField = field.getAnnotation(SQLField.class);
		if (sqlField == null || StringUtils.isBlank(sqlField.value())) {
			return field.getName();
		}
		return sqlField.value();
	}

	/**
	 * 获取字段类型
	 * 
	 * @param field
	 * @return
	 */
	public static SQLFieldType getSQLFieldType(Field field) {
		SQLField sqlField = field.getAnnotation(SQLField.class);
		SQLFieldType sqlFieldType = SQLFieldType.getSQLFieldType(field.getType());
		if (sqlField != null && sqlField.fieldType() != null && sqlField.fieldType() != SQLFieldType.NONE) {
			sqlFieldType = sqlField.fieldType();
		}
		return sqlFieldType;
	}

	/**
	 * 获取表名
	 * 
	 * @param cls
	 * @return
	 */
	public static <T> String getTableName(Class<T> cls) {
		SQLTable table = cls.getAnnotation(SQLTable.class);
		if (table == null || StringUtils.isBlank(table.value())) {
			return cls.getSimpleName();
		}
		return table.value();
	}

	/**
	 * 获取表字段
	 * 
	 * @param cls
	 * @return
	 */
	public static <T> List<Field> getTableFields(Class<T> cls) {
		Field[] fields = cls.getDeclaredFields();
		List<Field> tableFields = new ArrayList<Field>();
		for (Field field : fields) {
			NoSQLField noSqlField = field.getAnnotation(NoSQLField.class);
			if (noSqlField != null) {
				continue;
			}
			tableFields.add(field);
		}
		return tableFields;
	}

	/**
	 * 获取表字段
	 * 
	 * @param cls
	 * @param cmdType
	 *            支持命令
	 * @return
	 */
	public static <T> List<Field> getTableCmdFields(Class<T> cls, SQLFieldSupportCmdType cmdType) {
		List<Field> cmdFields = new ArrayList<Field>();
		for (Field field : getTableFields(cls)) {
			SQLField sqlField = field.getAnnotation(SQLField.class);
			SQLKeyField keyField = field.getAnnotation(SQLKeyField.class);
			if (cmdType == SQLFieldSupportCmdType.UPDATE && keyField != null) {
				continue;
			}
			if (sqlField == null || (sqlField.supportCmdTypes() | cmdType.getValue()) != sqlField.supportCmdTypes()) {
				continue;
			}
			cmdFields.add(field);
		}
		return cmdFields;
	}

	/**
	 * 获取 insert 命令字段
	 * 
	 * @param cls
	 * @return
	 */
	public static <T> List<Field> getTableInsertFields(Class<T> cls) {
		return getTableCmdFields(cls, SQLFieldSupportCmdType.INSERT);
	}

	/**
	 * 获取 select 命令字段
	 * 
	 * @param cls
	 * @return
	 */
	public static <T> List<Field> getTableSelectFields(Class<T> cls) {
		return getTableCmdFields(cls, SQLFieldSupportCmdType.SELECT);
	}

	/**
	 * 获取 update 命令字段
	 * 
	 * @param cls
	 * @return
	 */
	public static <T> List<Field> getTableUpdateFields(Class<T> cls) {
		return getTableCmdFields(cls, SQLFieldSupportCmdType.UPDATE);
	}

	/**
	 * 获取表键字段
	 * 
	 * @param cls
	 * @return
	 */
	public static <T> List<Field> getTableKeyFields(Class<T> cls) {
		List<Field> keyFields = new ArrayList<Field>();
		for (Field field : getTableFields(cls)) {
			SQLKeyField sqlField = field.getAnnotation(SQLKeyField.class);
			if (sqlField != null) {
				keyFields.add(field);
			}
		}
		return keyFields;
	}

	/**
	 * 获取表键字段
	 * 
	 * @param cls
	 * @param keyType
	 *            键类型
	 * @return
	 */
	public static <T> List<Field> getTableKeyFields(Class<T> cls, SQLKeyFieldType keyType) {
		List<Field> keyFields = new ArrayList<Field>();
		for (Field field : getTableFields(cls)) {
			SQLKeyField sqlField = field.getAnnotation(SQLKeyField.class);
			if (sqlField != null && sqlField.value() == keyType) {
				keyFields.add(field);
			}
		}
		return keyFields;
	}

	/**
	 * 获取主键字段
	 * 
	 * @param cls
	 * @return
	 */
	public static <T> List<Field> getTablePrimaryKeyFields(Class<T> cls) {
		return getTableKeyFields(cls, SQLKeyFieldType.PRIMARY);
	}

	/**
	 * 获取外键字段
	 * 
	 * @param cls
	 * @return
	 */
	public static <T> List<Field> getTableForeignKeyFields(Class<T> cls) {
		return getTableKeyFields(cls, SQLKeyFieldType.FOREIGN);
	}
}
