package com.cydeer.demo.memcached.test.repo;

import com.cydeer.demo.memcached.annotation.CacheEvictExt;
import com.cydeer.demo.memcached.annotation.CacheableExt;
import com.cydeer.demo.memcached.test.repo.domain.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangsong on 16/9/12.
 */
@Repository
public class UserInfoRepo {

	private final static Map<String, UserInfo> USER_INFO_MAP = new ConcurrentHashMap<>();

	@CacheableExt(keys = { "phone" }, expire = 5 * 60)
	public UserInfo findUserInfoByPhone(String phone) {
		return USER_INFO_MAP.get(phone);
	}

	@CacheableExt(keys = { "id" }, expire = 5 * 60)
	public UserInfo findUserInfoById(Integer id) {
		UserInfo result = null;
		for (UserInfo userInfo : USER_INFO_MAP.values()) {
			if (userInfo.getId().equals(id)) {
				result = userInfo;
				break;
			}
		}
		return result;
	}

	@CacheEvictExt(keys = { "id" }, secondKeys = { "phone" })
	public void save(UserInfo userInfo) {
		USER_INFO_MAP.put(userInfo.getPhone(), userInfo);
	}

	@CacheEvictExt(keys = { "id" }, secondKeys = { "phone" })
	public void update(UserInfo userInfo) {
		USER_INFO_MAP.put(userInfo.getPhone(), userInfo);
	}

}
