package com.cydeer.demo.memcached.annotation;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by zhangsong on 16/1/19.
 */
public class CacheKeyUtils {

	private final static Logger LOGGER = LoggerFactory.getLogger(CacheKeyUtils.class);
	private static final String UNDER_LINE = "_";
	private static final String POINT = ".";

	/**
	 * 类加方法名的唯一标识
	 *
	 * @param args
	 * @return
	 */
	public static String buildCacheKey(String keyExt, String[] keys, Class<?> targetClz,
			Object[] args) {
		String argsKey = "";
		if (args != null) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < args.length; i++) {
				Object arg = args[i];
				// 对于 args 有参数的方法，同时又是JSON生成键值的，必须定义包含的key值因子
				if (keys.length < 1)
					throw new IllegalArgumentException("请定义 keys 的缓存属性");
				if (arg == null) {
					sb.append(UNDER_LINE).append("null");
				} else {
					if (i < keys.length && StringUtils.isNotBlank(keys[i])) {
						if (isBassClassType(arg.getClass())) {
							sb.append(UNDER_LINE).append(String.valueOf(keys[i]));
							sb.append(UNDER_LINE).append(String.valueOf(arg));
						} else {
							Set<String> includeFields = new HashSet<String>(Arrays.asList(StringUtils.split(
									keys[i], ",")));
							Class<?> argClz = arg.getClass();
							//对于 Map 或 Collection，不支持
							//其他DTO对象排除复杂类型的属性数据
							if (!Map.class.isAssignableFrom(argClz) && !Collection.class.isAssignableFrom(argClz)) {
								Field[] argFields = arg.getClass().getDeclaredFields();
								for (Field field : argFields) {
									if (!isBassClassType(field.getType())) {
										includeFields.remove(field.getName());
									} else if (includeFields.contains(field.getName())) {
										field.setAccessible(true);
										try {
											sb.append(UNDER_LINE).append(field.getName()).append(UNDER_LINE)
											  .append(String.valueOf(field.get(arg)));
										} catch (IllegalAccessException e) {
											throw new IllegalArgumentException("key对应的值获取不到，key:" + field.getName());
										}
									}
								}
							}
						}
					}
				}
			}
			argsKey = sb.toString();
			argsKey = StringUtils.removeStart(argsKey, UNDER_LINE);
		}

		String wholeKey = StringUtils.isEmpty(keyExt) ?
				StringUtils.join(new String[] { targetClz.getName(), argsKey }, UNDER_LINE) :
				StringUtils.join(new String[] { targetClz.getName(), keyExt, argsKey }, UNDER_LINE);
		LOGGER.info("Cacheable for key : {}", wholeKey);
		return wholeKey;
	}

	private static boolean isBassClassType(Class<?> type) {
		return ClassUtils.isPrimitiveOrWrapper(type) || type == String.class;
	}
}
