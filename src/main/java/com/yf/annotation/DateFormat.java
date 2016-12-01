package com.yf.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
/**
 * 导出excle日期格式化
 * @author yang.f3
 *
 */
public @interface DateFormat {
	/**
	 * 日期格式化字符串
	 * @return
	 */
	String value() default "yyyy-MM-dd hh:mm:ss";
}
