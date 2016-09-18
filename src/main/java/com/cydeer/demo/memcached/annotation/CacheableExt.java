package com.cydeer.demo.memcached.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhangsong on 16/1/19.
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheableExt {

	/**
	 * @return 扩展 keys，如果同一个方法里面有相同的参数，防止key重复 暂时未开启
	 */
	String keyExt() default "";

	/**
	 * 使用规则：<BR>
	 * 1. 返回的是数组类型，数组中的每个值与参数中的对象一一对应。不过数组的长度允许与参数个数不同，<BR>
	 * 但实际运行时将以参数长度来自动匹配数据中相应位置的值；<BR>
	 * 2. 数组中的每个值，以英文逗号（,）分隔，用来区分对象中系列化要包含的多个字段；<BR>
	 * 3. 对于参数中若有对象不需要排除字段的，请以空字符串定义；<BR>
	 * 4. 复杂DTO对象中不支持再有复杂对象作为因子Key；<BR>
	 *
	 * @return 返回缓存排除JSON系统化KEY时的字段列表
	 */
	String[] keys() default {};

	/**
	 * @return 设置缓存过期时间，默认 60 秒
	 */
	int expire() default 60;

}
