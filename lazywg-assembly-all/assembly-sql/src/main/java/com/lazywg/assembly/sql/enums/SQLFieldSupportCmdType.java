package com.lazywg.assembly.sql.enums;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * author: gaowang
 *
 * createTime:2018年2月1日 上午9:17:05
 *
 */
public enum SQLFieldSupportCmdType {
	
	/**
	 * 增
	 */
	INSERT(1),
	
	/**
	 * 删除
	 */
	DELETE(2),
	
	/**
	 * 查询
	 */
	SELECT(4),
	
	/**
	 * 修改
	 */
	UPDATE(8);
	
	private int value;
	
	private static HashMap<Integer, SQLFieldSupportCmdType> mappings;

	private synchronized static HashMap<Integer, SQLFieldSupportCmdType> getMappings() {
		if (mappings == null) {
			mappings = new HashMap<Integer, SQLFieldSupportCmdType>();
		}
		return mappings;
	}

	private SQLFieldSupportCmdType(int value) {
		this.value = value;
		SQLFieldSupportCmdType.getMappings().put(value, this);
	}
	
	public int getValue(){
		return this.value;
	}
	
	public static SQLFieldSupportCmdType forValue(String value) throws IllegalArgumentException {
		for (Entry<Integer, SQLFieldSupportCmdType> item : mappings.entrySet()) {
			if (item.getValue().toString().equalsIgnoreCase(value)) {
				return item.getValue();
			}
		}
		throw new IllegalArgumentException(String.format("agr value:%s", value));
	}
	
	private static HashMap<Integer, SQLFieldSupportCmdType[]> cmdsMap = new  HashMap<Integer, SQLFieldSupportCmdType[]>();
	
	static{
		cmdsMap.put(1, new SQLFieldSupportCmdType[]{SQLFieldSupportCmdType.INSERT});
		cmdsMap.put(2, new SQLFieldSupportCmdType[]{SQLFieldSupportCmdType.DELETE});
		cmdsMap.put(3, new SQLFieldSupportCmdType[]{SQLFieldSupportCmdType.INSERT, SQLFieldSupportCmdType.DELETE});
		cmdsMap.put(4, new SQLFieldSupportCmdType[]{SQLFieldSupportCmdType.SELECT});
		cmdsMap.put(5, new SQLFieldSupportCmdType[]{SQLFieldSupportCmdType.INSERT, SQLFieldSupportCmdType.SELECT});
		cmdsMap.put(6, new SQLFieldSupportCmdType[]{SQLFieldSupportCmdType.DELETE, SQLFieldSupportCmdType.SELECT});
		cmdsMap.put(7, new SQLFieldSupportCmdType[]{SQLFieldSupportCmdType.INSERT, SQLFieldSupportCmdType.DELETE, SQLFieldSupportCmdType.SELECT});
		cmdsMap.put(8,  new SQLFieldSupportCmdType[]{SQLFieldSupportCmdType.UPDATE});
		cmdsMap.put(9,  new SQLFieldSupportCmdType[]{SQLFieldSupportCmdType.INSERT, SQLFieldSupportCmdType.UPDATE});
		cmdsMap.put(10,  new SQLFieldSupportCmdType[]{SQLFieldSupportCmdType.DELETE, SQLFieldSupportCmdType.UPDATE});
		cmdsMap.put(11,  new SQLFieldSupportCmdType[]{SQLFieldSupportCmdType.INSERT, SQLFieldSupportCmdType.DELETE, SQLFieldSupportCmdType.UPDATE});
		cmdsMap.put(12,  new SQLFieldSupportCmdType[]{SQLFieldSupportCmdType.SELECT, SQLFieldSupportCmdType.UPDATE});
		cmdsMap.put(13,  new SQLFieldSupportCmdType[]{SQLFieldSupportCmdType.INSERT, SQLFieldSupportCmdType.SELECT, SQLFieldSupportCmdType.UPDATE});
		cmdsMap.put(14,  new SQLFieldSupportCmdType[]{SQLFieldSupportCmdType.DELETE, SQLFieldSupportCmdType.SELECT, SQLFieldSupportCmdType.UPDATE});
		cmdsMap.put(15,  new SQLFieldSupportCmdType[]{SQLFieldSupportCmdType.INSERT, SQLFieldSupportCmdType.DELETE, SQLFieldSupportCmdType.SELECT, SQLFieldSupportCmdType.UPDATE});
	}
	
	public static SQLFieldSupportCmdType[] getSupportCmdTypes(int value) {
		if (cmdsMap.containsKey(value)) {
			return cmdsMap.get(value);
		}
		throw new IllegalArgumentException(String.format("agr value:%s", value));
	}
}
