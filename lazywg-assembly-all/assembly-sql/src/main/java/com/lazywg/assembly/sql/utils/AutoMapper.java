package com.lazywg.assembly.sql.utils;

import com.lazywg.assembly.sql.annotation.NoSQLField;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author: gaowang
 *
 * createTime:2018年2月1日 下午5:44:32
 * 
 * ResultSet 映射到 实体类
 */
public class AutoMapper {
	
	private static Logger logger = Logger.getLogger(AutoMapper.class);
	
	/**
	 * 获取单个实体
	 * 
	 * @param resultSet
	 * @param cls
	 * @return
	 */
	public static <T> T entityMapper(ResultSet resultSet, Class<T> cls) {
		List<Field> entityFields = getEntityTableFields(cls);
		Method[] entityMethods = SQLClassUtil.getEntityMethods(cls);
		T t = null;
		try {
			t = cls.newInstance();
			logger.debug(String.format("entityMapper [%s] begin.",cls.getName()));
			for (Field field : entityFields) {
				Object fieldVal = getFieldObject(resultSet, field);
				Method method = SQLClassUtil.getFieldSetMethod(entityMethods, field);
				if (method != null && fieldVal != null) {
					method.invoke(t, fieldVal);
				}
			}
			logger.debug(String.format("entityMapper [%s] end.",cls.getName()));
		} catch (Exception e) {
			logger.error("entityMapper",e);
		}
		return t;
	}

	/**
	 * 获取多个实体
	 * 
	 * @param resultSet
	 * @param cls
	 * @return
	 */
	public static <T> List<T> entityListMapper(ResultSet resultSet, Class<T> cls) {
		List<T> entityList = new ArrayList<T>();
		try {
			while (resultSet.next()) {
				entityList.add(entityMapper(resultSet, cls));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entityList;
	}

	/**
	 * 获取实体表字段
	 * 
	 * @param cls
	 * @return
	 */
	private static <T> List<Field> getEntityTableFields(Class<T> cls) {
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
	 * 
	 * @param resultSet
	 * @param field
	 * @return
	 */
	private static Object getFieldObject(ResultSet resultSet, Field field) {
		try {
			if (field.getType().getName().equalsIgnoreCase(Date.class.getName())) {
				return resultSet.getTimestamp(SQLClassUtil.getSQLFieldName(field));
			}
			Object val = resultSet.getObject(SQLClassUtil.getSQLFieldName(field));
			if (val instanceof BigDecimal) {
				return convertToNumberOrBool((BigDecimal) val, field);
			}
			return val;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object convertToDate(Object val) {
		if (val instanceof Timestamp) {
			Timestamp tsm = (Timestamp) val;
			return new Date(tsm.getTime());
		}
		if (val instanceof java.sql.Date) {
			java.sql.Date date = (java.sql.Date) val;
			return new Date(date.getTime());
		}
		return val;
	}

	private static Object convertToNumberOrBool(BigDecimal val, Field field) {
		String filedTypeName = field.getType().getName();
		if (filedTypeName.equalsIgnoreCase(Integer.class.getName()) || filedTypeName.equalsIgnoreCase(int.class.getName())) {
			return val.intValueExact();
		}
		if (filedTypeName.equalsIgnoreCase(Byte.class.getName()) || filedTypeName.equalsIgnoreCase(byte.class.getName())) {
			return val.byteValueExact();
		}
		if (filedTypeName.equalsIgnoreCase(Short.class.getName()) || filedTypeName.equalsIgnoreCase(short.class.getName())) {
			return val.shortValueExact();
		}
		if (filedTypeName.equalsIgnoreCase(Long.class.getName()) || filedTypeName.equalsIgnoreCase(long.class.getName())) {
			return val.longValueExact();
		}
		if (filedTypeName.equalsIgnoreCase(Double.class.getName()) || filedTypeName.equalsIgnoreCase(double.class.getName())) {
			return val.doubleValue();
		}
		if (filedTypeName.equalsIgnoreCase(Float.class.getName()) || filedTypeName.equalsIgnoreCase(float.class.getName())) {
			return val.floatValue();
		}
		if (filedTypeName.equalsIgnoreCase(Boolean.class.getName()) || filedTypeName.equalsIgnoreCase(boolean.class.getName())) {
			return val.intValue() == 0 ? false : true;
		}
		return val;
	}
}
