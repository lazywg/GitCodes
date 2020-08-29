package com.lazywg.assembly.sql.entity;

/**
 * @author: gaowang
 *
 * @createTime: 2019年1月17日 下午8:47:01
 *
 */
public class Table {

	private String tableName;

	private String tableComment;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
}
