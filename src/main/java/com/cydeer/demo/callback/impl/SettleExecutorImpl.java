package com.cydeer.demo.callback.impl;

import com.cydeer.demo.callback.api.CallBack;
import com.cydeer.demo.callback.api.SettleExecutor;

/**
 * Created by zhangsong on 16/8/21.
 * 业务执行器实现
 */
public class SettleExecutorImpl implements SettleExecutor {

	@Override public void asyncExecute(Object o, CallBack<?> callBack) {
		// 使用新线程异步执行
		new Thread(new Runnable() {
			@Override public void run() {
				execute(o);
				callBack.invoke();
			}
		}).start();
	}

	@Override public <T> T syncExecute(Object o, CallBack<T> callBack) {
		execute(o);
		return callBack.invoke();
	}

	/**
	 * 实际的业务执行逻辑
	 *
	 * @param o
	 */
	private void execute(Object o) {
		System.out.println("参数检查");
		System.out.println("业务逻辑一");
		System.out.println("业务逻辑二");
		System.out.println("业务逻辑执行结束");
	}
}
