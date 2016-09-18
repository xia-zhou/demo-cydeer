package com.cydeer.demo.memcached.test;

import com.cydeer.demo.memcached.test.repo.UserInfoRepo;
import com.cydeer.demo.memcached.test.repo.domain.UserInfo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zhangsong on 16/9/12.
 */
public class MainTestClient {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/*.xml");
		UserInfoRepo userInfoRepo = applicationContext.getBean(UserInfoRepo.class);
		UserInfo userInfo = userInfoRepo.findUserInfoById(10);
		System.out.println(userInfo);
		userInfo = new UserInfo(10, "cydeer", "187");
		userInfoRepo.save(userInfo);
		System.out.println(userInfoRepo.findUserInfoById(10));
		System.out.println("第二次调用");
		System.out.println(userInfoRepo.findUserInfoById(10));
		System.out.println(userInfoRepo.findUserInfoByPhone("187"));
		userInfo.setName("xiazhou");
		userInfoRepo.update(userInfo);
		System.out.println(userInfoRepo.findUserInfoByPhone("187"));
	}
}
