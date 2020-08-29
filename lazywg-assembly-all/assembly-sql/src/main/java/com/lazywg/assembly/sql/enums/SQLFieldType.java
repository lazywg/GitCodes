package com.lazywg.assembly.sql.enums;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * author: gaowang
 *
 * createTime:2018年1月31日 上午10:45:25
 *
 */
public enum SQLFieldType {

	NONE(-1),

	/**
	 * 字符串
	 */
	STRING(0),

	/**
	 * int 数字
	 */
	INT(1),

	/**
	 * 金钱
	 */
	MONEY(2),

	/**
	 * 单精度浮点
	 */
	FLOAT(3),

	/**
	 * 双精度浮点
	 */
	DOUBLE(4),

	/**
	 * 长整型
	 */
	LONG(5),

	/**
	 * 文本
	 */
	TEXT(6),

	/**
	 * boolean 类型
	 */
	BOOL(7),

	/**
	 * 全球唯一Id
	 */
	GUID(8),

	CHAR(9),

	BYTE(10),

	DATETIME(11),

	DECIMAL(12);

	private int value;

	private static HashMap<Integer, SQLFieldType> mappings;

	private synchronized static HashMap<Integer, SQLFieldType> getMappings() {
		if (mappings == null) {
			mappings = new HashMap<Integer, SQLFieldType>();
		}
		return mappings;
	}

	private SQLFieldType(int value) {
		this.value = value;
		SQLFieldType.getMappings().put(value, this);
	}

	public int getValue() {
		return this.value;
	}

	public static SQLFieldType forValue(String value) throws IllegalArgumentException {
		for (Entry<Integer, SQLFieldType> item : mappings.entrySet()) {
			if (item.getValue().toString().equalsIgnoreCase(value)) {
				return item.getValue();
			}
		}
		throw new IllegalArgumentException(String.format("agr value:%s", value));
	}

	private static Map<String, SQLFieldType> typeMap = new HashMap<String, SQLFieldType>();
	private static Map<SQLFieldType, Object> defaultValMap = new HashMap<SQLFieldType, Object>();

	static {
		typeMap.put(Integer.class.getName(), SQLFieldType.INT);
		typeMap.put(int.class.getName(), SQLFieldType.INT);
		typeMap.put(Float.class.getName(), SQLFieldType.FLOAT);
		typeMap.put(float.class.getName(), SQLFieldType.FLOAT);
		typeMap.put(Double.class.getName(), SQLFieldType.DOUBLE);
		typeMap.put(double.class.getName(), SQLFieldType.DOUBLE);
		typeMap.put(Boolean.class.getName(), SQLFieldType.BOOL);
		typeMap.put(boolean.class.getName(), SQLFieldType.BOOL);
		typeMap.put(String.class.getName(), SQLFieldType.STRING);
		typeMap.put(Long.class.getName(), SQLFieldType.LONG);
		typeMap.put(long.class.getName(), SQLFieldType.LONG);
		typeMap.put(UUID.class.getName(), SQLFieldType.GUID);
		typeMap.put(Character.class.getName(), SQLFieldType.CHAR);
		typeMap.put(char.class.getName(), SQLFieldType.CHAR);
		typeMap.put(Byte.class.getName(), SQLFieldType.BYTE);
		typeMap.put(byte.class.getName(), SQLFieldType.BYTE);
		typeMap.put(Date.class.getName(), SQLFieldType.DATETIME);
		typeMap.put(java.sql.Date.class.getName(), SQLFieldType.DATETIME);
		typeMap.put(BigDecimal.class.getName(), SQLFieldType.DECIMAL);

		defaultValMap.put(SQLFieldType.INT, 0);
		defaultValMap.put(SQLFieldType.FLOAT, 0);
		defaultValMap.put(SQLFieldType.DOUBLE, 0);
		defaultValMap.put(SQLFieldType.BOOL, false);
		defaultValMap.put(SQLFieldType.STRING, "");
		defaultValMap.put(SQLFieldType.LONG, 0);
		defaultValMap.put(SQLFieldType.GUID, "");
		defaultValMap.put(SQLFieldType.CHAR, 0);
		defaultValMap.put(SQLFieldType.BYTE, 0);
		defaultValMap.put(SQLFieldType.DATETIME, new Date(1770, 1, 1));
		defaultValMap.put(SQLFieldType.DECIMAL,0);
	}

	public static SQLFieldType getSQLFieldType(Class<?> cls) {
		if (typeMap.containsKey(cls.getName())) {
			return typeMap.get(cls.getName());
		}
		return null;
	}

	public static Object getSQLFieldTypeDefaultVal(SQLFieldType sqlFieldType) {
		if (defaultValMap.containsKey(sqlFieldType)) {
			return defaultValMap.get(sqlFieldType);
		}
		return null;
	}
}
