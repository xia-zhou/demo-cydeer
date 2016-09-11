package com.cydeer.demo.onebyone.api;

import com.cydeer.demo.onebyone.api.domain.OneByOne;

/**
 * Created by zhangsong on 16/9/11.
 */
public interface OneByOneExecutor {
	/**
	 * @param oneByOne 业务场景,不同的业务场景不同的锁
	 * @param callBack 实际在获取到锁的范围内执行的业务逻辑
	 * @param <T>      业务逻辑的返回结果。
	 * @return
	 */
	<T> T execute(OneByOne oneByOne, CallBack<T> callBack);
}
