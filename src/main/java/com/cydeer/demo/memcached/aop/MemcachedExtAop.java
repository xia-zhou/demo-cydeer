package com.cydeer.demo.memcached.aop;

import com.cydeer.demo.memcached.annotation.CacheEvictExt;
import com.cydeer.demo.memcached.annotation.CacheKeyUtils;
import com.cydeer.demo.memcached.annotation.CacheableExt;
import com.cydeer.demo.memcached.client.CacheApi;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Created by zhangsong on 16/1/20.
 */
@Aspect
public class MemcachedExtAop {
	private final static Logger LOGGER = LoggerFactory.getLogger(MemcachedExtAop.class);

	private CacheableExt cacheableExt;
	private CacheEvictExt cacheEvictExt;

	private CacheApi cacheApi;

	@Around("@annotation(com.cydeer.demo.memcached.annotation.CacheableExt)")
	private Object cacheableProcess(ProceedingJoinPoint jp) throws Throwable {

		Class<?> targetClz = jp.getTarget().getClass();

		String methodName = jp.getSignature().getName();

		if (!(jp.getSignature() instanceof MethodSignature)) {
			LOGGER.warn("该方法接口无法启用缓存功能: {}", jp.getSignature().toLongString());
			return jp.proceed();
		}
		MethodSignature methodSign = (MethodSignature) jp.getSignature();
		Method method = targetClz.getMethod(methodName, methodSign.getParameterTypes());
		cacheableExt = method.getAnnotation(CacheableExt.class);
		if (cacheableExt == null) {
			return jp.proceed();
		}
		if (cacheableExt.expire() > 0) {
			String cacheKey = CacheKeyUtils
					.buildCacheKey(cacheableExt.keyExt(), cacheableExt.keys(), targetClz, jp.getArgs());
			Object result = cacheApi.get(cacheKey);
			if (result == null) {
				result = jp.proceed();
				result = result == null ? Blank.INST : result;
				cacheApi.put(cacheKey, result, cacheableExt.expire());
			}
			return result instanceof Blank ? null : result;
		} else {
			return jp.proceed();
		}
	}

	@Around("@annotation(com.cydeer.demo.memcached.annotation.CacheEvictExt)")
	private Object cacheevictProcess(ProceedingJoinPoint jp) throws Throwable {

		Class<?> targetClz = jp.getTarget().getClass();

		String methodName = jp.getSignature().getName();

		if (!(jp.getSignature() instanceof MethodSignature)) {
			LOGGER.warn("该方法接口无法启用缓存功能: {}", jp.getSignature().toLongString());
			return jp.proceed();
		}
		MethodSignature methodSign = (MethodSignature) jp.getSignature();
		Method method = targetClz.getMethod(methodName, methodSign.getParameterTypes());
		cacheEvictExt = method.getAnnotation(CacheEvictExt.class);
		if (cacheEvictExt != null) {
			String cacheKey = CacheKeyUtils
					.buildCacheKey(cacheEvictExt.keyExt(), cacheEvictExt.keys(), targetClz, jp.getArgs());
			cacheApi.evict(cacheKey);
			// 清除 第二个 key
			if (cacheEvictExt.secondKeys() == null || cacheEvictExt.secondKeys().length > 0) {
				String cacheKeyBackUp = CacheKeyUtils
						.buildCacheKey(cacheEvictExt.keyExt(), cacheEvictExt.secondKeys(), targetClz, jp.getArgs());
				cacheApi.evict(cacheKeyBackUp);
			}
		}
		return jp.proceed();
	}

	public void setCacheApi(CacheApi cacheApi) {
		this.cacheApi = cacheApi;
	}

	private static class Blank implements Serializable {
		private static final long serialVersionUID = 3203712628835590212L;
		private static final MemcachedExtAop.Blank INST = new MemcachedExtAop.Blank();

		private Blank() {
		}
	}
}
