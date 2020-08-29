package com.lazywg.assembly.sql.annotation;

import com.lazywg.assembly.sql.enums.SQLKeyFieldType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author: gaowang
 *
 * createTime:2018年2月1日 上午9:09:57
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLKeyField {

	SQLKeyFieldType value() default SQLKeyFieldType.PRIMARY;
}
