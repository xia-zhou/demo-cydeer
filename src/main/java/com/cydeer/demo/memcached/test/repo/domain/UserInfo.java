package com.cydeer.demo.memcached.test.repo.domain;

import java.io.Serializable;

/**
 * Created by zhangsong on 16/9/12.
 */
public class UserInfo implements Serializable {

	/**
	 * 用户id
	 */
	private Integer id;

	/**
	 * 用户姓名
	 */
	private String name;

	/**
	 * 用户手机号
	 */
	private String phone;

	public UserInfo(Integer id, String name, String phone) {
		this.id = id;
		this.name = name;
		this.phone = phone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("            \"id\":").append(id);
		sb.append(",             \"name\":\"").append(name).append('\"');
		sb.append(",             \"phone\":\"").append(phone).append('\"');
		sb.append('}');
		return sb.toString();
	}
}
