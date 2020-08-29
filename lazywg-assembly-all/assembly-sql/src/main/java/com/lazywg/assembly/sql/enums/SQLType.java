package com.lazywg.assembly.sql.enums;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * 	sqlserver,
	mysql,
	oracle
	mongodb
	
 * @author gaowang
 *
 * 数据库类型
 * 
 */
public enum SQLType {
	
	/**
	 * Sql Server
	 */
	SQLSERVER(1),
	
	/**
	 * mysql
	 */
	MYSQL(2),
	
	/**
	 * oracle
	 */
	ORACLE(3);
	
	private int value;
	
	private static HashMap<Integer, SQLType> mappings;

	private synchronized static HashMap<Integer, SQLType> getMappings() {
		if (mappings == null) {
			mappings = new HashMap<Integer, SQLType>();
		}
		return mappings;
	}

	private SQLType(int value) {
		this.value = value;
		SQLType.getMappings().put(value, this);
	}
	
	public int getValue(){
		return this.value;
	}
	
	public static SQLType forValue(String value) throws IllegalArgumentException {
		for (Entry<Integer, SQLType> item : mappings.entrySet()) {
			if (item.getValue().toString().equalsIgnoreCase(value)) {
				return item.getValue();
			}
		}
		throw new IllegalArgumentException(String.format("agr value:%s", value));
	}

	public static SQLType forValue(int dbType) {
		if (mappings.containsKey(dbType)) {
			return mappings.get(dbType);
		}
		throw new IllegalArgumentException(String.format("agr value:%d", dbType));
	}
}
