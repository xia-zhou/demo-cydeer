package com.cydeer.demo.rmi.zk;

/**
 * Created by zhangsong on 16/8/11.
 * 链接zk的使用敞亮
 */
public interface Constant {
	// zookeeper地址
	String connectString = "127.0.0.1:22181";
	// 超时时间
	Integer timeOut = 5000;
	/**
	 * 提供者注册的父节点
	 */
	String path = "/registry";

}
