package com.cydeer.demo.onebyone;

import com.cydeer.demo.onebyone.api.CallBack;
import com.cydeer.demo.onebyone.api.OneByOneExecutor;
import com.cydeer.demo.onebyone.api.domain.OneByOne;
import com.cydeer.demo.onebyone.impl.OneByOneExecutorImpl;

/**
 * Created by zhangsong on 16/9/11.
 */
public class MainClient {
	public static void main(String[] args) {
		OneByOneExecutor oneByOneExecutor = new OneByOneExecutorImpl();
		Object object = oneByOneExecutor
				.execute(new OneByOne("SETTLE_TASK", "201609", "9月份定时任务执行"), new CallBack<Object>() {
					@Override public Object invoke() {
						System.out.println("真正的业务逻辑代码");
						// 业务执行结果
						Object o = new Object();
						return o;
					}
				});
		
	}
}
