package com.lazywg.assembly.sql.entity;

import com.lazywg.assembly.sql.enums.SQLFieldType;

/**
 * author: gaowang
 *
 * createTime:2018年1月31日 上午11:22:19
 * @param <T>
 *
 */
public class SQLParam<T> {

	private String fieldName;
	private T fieldValue;
	private SQLFieldType fieldType;
	
	public SQLParam(){}
	
	public SQLParam(String fieldName,T fieldValue,SQLFieldType fieldType){
		this.fieldName = fieldName;
		this.fieldValue =fieldValue;
		this.fieldType = fieldType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public T getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(T fieldValue) {
		this.fieldValue = fieldValue;
	}

	public SQLFieldType getFieldType() {
		return fieldType;
	}

	public void setFieldType(SQLFieldType fieldType) {
		this.fieldType = fieldType;
	}
}
