package com.cydeer.demo.memcached.client;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

/**
 * Created by zhangsong on 16/1/19.
 */
public class MemcachedClientFactory implements FactoryBean<MemcachedClientExt>, InitializingBean {

	private final static Logger LOGGER = LoggerFactory.getLogger(MemcachedClientFactory.class);
	private MemcachedClientExt memcachedClientExt;

	private String servers;
	private String keyPrefix;
	private int defaultExpire;
	/*private long timeOut;
	private boolean auth;
	private String username;
	private String password;*/

	@Override public MemcachedClientExt getObject() throws Exception {
		return memcachedClientExt;
	}

	@Override public Class<?> getObjectType() {
		return MemcachedClientExt.class;
	}

	@Override public boolean isSingleton() {
		return true;
	}

	@Override public void afterPropertiesSet() throws Exception {
		if (StringUtils.isEmpty(servers)) {
			throw new IllegalArgumentException("No servers set");
		}
		if (StringUtils.isEmpty(keyPrefix)) {
			throw new IllegalArgumentException("Please set key prefux");
		}
		if (defaultExpire == 0) {
			// 过期时间 默认设置为1分钟（60秒）
			defaultExpire = 60;
		}
		MemcachedClient client = new MemcachedClient(AddrUtil.getAddresses(servers));
		this.memcachedClientExt = new MemcachedClientExt(client, keyPrefix, defaultExpire);
		LOGGER.info("MemcachedClientExt has init");
	}

	public void setServers(String servers) {
		this.servers = servers;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	public void setDefaultExpire(int defaultExpire) {
		this.defaultExpire = defaultExpire;
	}
}
