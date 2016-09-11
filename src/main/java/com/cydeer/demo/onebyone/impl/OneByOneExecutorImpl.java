package com.cydeer.demo.onebyone.impl;

import com.cydeer.demo.onebyone.api.CallBack;
import com.cydeer.demo.onebyone.api.ObtainLockException;
import com.cydeer.demo.onebyone.api.OneByOneExecutor;
import com.cydeer.demo.onebyone.api.domain.OneByOne;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.BadSqlGrammarException;

import java.util.Date;

/**
 * Created by zhangsong on 16/9/11.
 */
public class OneByOneExecutorImpl implements OneByOneExecutor {
	/**
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(OneByOneExecutorImpl.class);

	/**
	 * 插入结果
	 */
	private ThreadLocal<Boolean> insertResult = new ThreadLocal<Boolean>();

	/**
	 * 业务描述
	 */
	private ThreadLocal<String> description = new ThreadLocal<String>();

	@Override public <T> T execute(OneByOne oneByOne, CallBack<T> callBack) {
		StringBuilder builder = new StringBuilder(64);
		builder.append(oneByOne.getBizType()).append("-").append(oneByOne.getBizId()).append("-")
		       .append(oneByOne.getMethod());
		this.description.set(builder.toString());

		try {
			this.beforeInvoke(oneByOne); // 前处理

			return callBack.invoke(); // 业务逻辑
		} finally {
			this.afterInvoke(oneByOne); // 后处理
		}
	}

	/**
	 * 前处理<br>
	 * 尝试插入处理记录:加锁
	 *
	 * @param oneByOne 一个接一个业务实体
	 */
	private void beforeInvoke(final OneByOne oneByOne) {
		try {
			insertResult.set(Boolean.TRUE);

			// 插入处理记录
			oneByOne.setCreateTime(new Date()); // 创建时间
			save(oneByOne);
		} catch (BadSqlGrammarException e) { // SQL语法错误或表不存在: 直接执行业务逻辑,不抛异常
			insertResult.set(Boolean.FALSE);

			if (LOG.isWarnEnabled()) {
				LOG.warn(description.get() + "插处理记录失败", e);
			}
		} catch (Throwable t) {
			insertResult.set(Boolean.FALSE);

			if (LOG.isWarnEnabled()) {
				LOG.warn(description.get() + "插处理记录失败", t);
			}
			// 抛出异常
			throw new ObtainLockException("业务正在处理中");
		}

	}

	/**
	 * 后处理<br>
	 * 删除处理记录:释放锁
	 *
	 * @param oneByOne 一个接一个业务实体
	 */
	private void afterInvoke(final OneByOne oneByOne) {
		// beforeInvoke时插入失败,不需删除处理记录
		if (!insertResult.get()) {
			return;
		}

		try {
			// 删除处理记录
			delete(oneByOne);
		} catch (Throwable t) {
			LOG.error(description.get() + "删处理记录失败", t);
		} finally {
			// 清理
			description.set(null);
			insertResult.set(null);
		}

	}

	private void save(OneByOne oneByOne) {
		System.out.println("存储数据库,存储成功获得锁,存储失败获取锁失败");
	}

	private void delete(OneByOne oneByOne) {
		System.out.println("删除记录,释放锁");
	}

}
