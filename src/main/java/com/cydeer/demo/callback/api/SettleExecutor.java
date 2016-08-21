package com.cydeer.demo.callback.api;

/**
 * Created by zhangsong on 16/8/21.
 * 业务执行器 接口
 */
public interface SettleExecutor {

	/**
	 * 异步执行
	 *
	 * @param o
	 * @param callBack
	 * @param <>
	 * @return
	 */
	void asyncExecute(Object o, CallBack<?> callBack);

	/**
	 * 同步执行
	 *
	 * @param o
	 * @param callBack
	 * @param <T>
	 * @return
	 */
	<T> T syncExecute(Object o, CallBack<T> callBack);
}
