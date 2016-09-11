package com.cydeer.demo.onebyone.api;

/**
 * Created by zhangsong on 16/9/11.
 * 获取锁异常
 */
public class ObtainLockException extends RuntimeException {
	public ObtainLockException(String message) {
		super(message);
	}
}
