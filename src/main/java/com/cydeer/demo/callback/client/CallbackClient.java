package com.cydeer.demo.callback.client;

import com.cydeer.demo.callback.api.CallBack;
import com.cydeer.demo.callback.api.SettleExecutor;
import com.cydeer.demo.callback.impl.SettleExecutorImpl;

/**
 * Created by zhangsong on 16/8/21.
 */
public class CallbackClient {

	public static void main(String[] args) {
		Object param = new Object();
		System.out.println("执行调用方自己的业务逻辑");
		SettleExecutor settleExecutor = new SettleExecutorImpl();
		// 异步调用,拿不到返回结果
		settleExecutor.asyncExecute(param, new CallBack<Boolean>() {
			@Override public Boolean invoke() {
				System.out.println("第三方业务逻辑调用完成过,执行调用方自己的业务逻辑");
				return Boolean.TRUE;
			}
		});
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 同步调用 可以拿到返回结果,返回结果由调用方决定,返回回调函数中的返回类型
		Object result = settleExecutor.syncExecute(param, new CallBack<Object>() {
			@Override public Object invoke() {
				System.out.println("第三方业务逻辑调用完成过,执行调用方自己的业务逻辑");
				return new Object();
			}
		});
	}
}
