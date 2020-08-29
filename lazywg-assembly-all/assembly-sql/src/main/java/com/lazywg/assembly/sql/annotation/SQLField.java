package com.lazywg.assembly.sql.annotation;


import com.lazywg.assembly.sql.enums.SQLFieldType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author: gaowang
 *
 * createTime:2018年1月31日 上午10:43:49
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLField {

	/**
	 * 字段名称
	 * 
	 * @return
	 */
	String value() default "";
	
	/**
	 * 字段类型
	 * 
	 * @return
	 */
	SQLFieldType fieldType() default SQLFieldType.NONE;
	
	/**
	 * 长度
	 * 
	 * @return
	 */
	int length() default 0;
	
	/**
	 * 值范围在1~15
	 * 
	 * @valueRange insert=1 | delete=2 | select=4 | update=8
	 * 
	 * @return
	 */
	int supportCmdTypes() default (1 | 2 | 4 | 8);
}
