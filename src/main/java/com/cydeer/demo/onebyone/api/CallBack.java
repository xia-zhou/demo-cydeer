package com.cydeer.demo.onebyone.api;

/**
 * Created by zhangsong on 16/9/11.
 */
public interface CallBack<T> {
	/**
	 * 业务处理回调,在锁范围内执行的业务逻辑
	 *
	 * @param <T>
	 * @return
	 */
	<T> T invoke();
}
