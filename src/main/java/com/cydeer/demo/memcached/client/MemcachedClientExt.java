package com.cydeer.demo.memcached.client;

import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;

/**
 * Created by zhangsong on 16/1/19.
 */
public class MemcachedClientExt implements CacheApi {

	/**
	 * 使用net.spy.memcached.MemcachedClient 的客户端
	 */
	private MemcachedClient client;

	/**
	 * key 的前缀，防止不同应用使用相同的缓存服务器，key重复。
	 */
	private String keyPrefix;

	private int defaultExpire;

	public MemcachedClientExt(MemcachedClient client, String keyPrefix, int defaultExpire) {
		this.client = client;
		this.keyPrefix = keyPrefix + ".";
		this.defaultExpire = defaultExpire;
	}

	@Override public Object get(String key) {
		return client.get(buildKey(key));
	}

	@Override public <T> T get(String key, Class<T> type) {
		Object value = this.get(key);
		if (value != null && type != null && !type.isInstance(value)) {
			throw new IllegalStateException("Cached value is not of required type [" + type.getName() + "]: " + value);
		}
		return (T) value;
	}

	@Override public void put(String key, Object value) {
		this.put(key, value, defaultExpire);
	}

	@Override public void put(String key, Object value, int expire) {
		OperationFuture<Boolean> res = client.set(buildKey(key), expire, value);
		if (res.isDone()) {
			//System.out.println("zhaong");
		}
	}

	@Override public void evict(String key) {
		client.delete(buildKey(key));
	}

	private String buildKey(String key) {
		return keyPrefix + key;
	}
}
