package com.yf.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
/**
 * 导出排序
 * @author yang.f3
 *
 */
public @interface OrderNumber {
	int  value();
}
