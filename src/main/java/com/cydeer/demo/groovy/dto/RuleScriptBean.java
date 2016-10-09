package com.cydeer.demo.groovy.dto;

import com.cydeer.demo.groovy.api.SettleRule;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangsong on 16/10/9.
 * <p>
 * 脚本缓存
 */
public class RuleScriptBean implements Serializable {

	/**
	 * classLoad 加载groovy之后的class
	 */
	private Class<SettleRule> clazz;

	/**
	 * 上次修改时间
	 */
	private Date lastModifyTime;

	public RuleScriptBean(Class<SettleRule> clazz, Date lastModifyTime) {
		this.clazz = clazz;
		this.lastModifyTime = lastModifyTime;
	}

	public Class<SettleRule> getClazz() {
		return clazz;
	}

	public void setClazz(Class<SettleRule> clazz) {
		this.clazz = clazz;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	@Override public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("  \"clazz\":").append(clazz);
		sb.append(", \"lastModifyTime\":\"").append(lastModifyTime).append('\"');
		sb.append('}');
		return sb.toString();
	}
}
