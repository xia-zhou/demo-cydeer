package com.cydeer.demo.memcached.client;

/**
 * Created by zhangsong on 16/1/19.
 */
public interface CacheApi {

	Object get(String key);

	<T> T get(String key, Class<T> type);

	void put(String key, Object value);

	void put(String key, Object value, int expire);

	void evict(String key);
}
