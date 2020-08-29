package com.lazywg.assembly.sql.entity;

/**
 * @author: gaowang
 *
 * @createTime: 2019年1月17日 下午8:47:10
 *
 */
public class Column {

	private String columnName;
	
	private String columnType;
	
	private String columnKey;
	
	private String columnComment;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
}

