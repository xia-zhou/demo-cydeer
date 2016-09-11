package com.cydeer.demo.onebyone.api.domain;

import java.util.Date;

/**
 * Created by zhangsong on 16/9/11.
 */
public class OneByOne {
	/**
	 * 业务类型
	 */
	private String bizType;

	/**
	 * 业务Id 一般为帐号ID,和上面的bizType组成联合主键
	 */
	private String bizId;

	/**
	 * 方法名称
	 */
	private String method;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * @param bizType 业务类型
	 * @param bizId   业务Id
	 * @param method  方法名称
	 */
	public OneByOne(String bizType, String bizId, String method) {
		this.bizType = bizType;
		this.bizId = bizId;
		this.method = method;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
