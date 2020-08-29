package com.lazywg.assembly.sql.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author: gaowang
 *
 * createTime:2018年1月31日 上午10:40:45
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SQLTable {

	String value() default "";
}
