package com.cydeer.demo.groovy.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhangsong on 16/10/9.
 * 规则脚本实体,规则脚本可以存储在mysql中,可以存在redis中,样例是存在mysql中的。
 * 存储在redis中的解决方案:
 * redis结构为hashMap接口,外层key为某个特殊含义的标识,内层key为查询脚本的条件,维度,value为该实体的json字符串,
 * 或者只是脚本和上次更新时间
 */
public class RuleScriptEntity implements Serializable {

	/**
	 * 唯一主键
	 */
	private Integer id;

	/**
	 * 查询脚本的条件,比如说一个脚本每个城市都不一样
	 */
	private Integer cityId;

	/**
	 * 具体的脚本信息
	 */
	private String script;

	/**
	 * 脚本的名字
	 */
	private String scriptName;
	/**
	 * 上次更新时间 主要为了缓存,如果上次更新时间一直没有变化就不需要重新加载。
	 */
	private Date lastModifyTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getScriptName() {
		return scriptName;
	}

	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	@Override public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("  \"id\":").append(id);
		sb.append(", \"cityId\":").append(cityId);
		sb.append(", \"script\":\"").append(script).append('\"');
		sb.append(", \"scriptName\":\"").append(scriptName).append('\"');
		sb.append(", \"lastModifyTime\":\"").append(lastModifyTime).append('\"');
		sb.append('}');
		return sb.toString();
	}
}
