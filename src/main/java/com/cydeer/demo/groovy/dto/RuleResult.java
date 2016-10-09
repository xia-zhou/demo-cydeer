package com.cydeer.demo.groovy.dto;

import java.io.Serializable;

/**
 * Created by zhangsong on 16/10/9.
 * 输出结果
 */
public class RuleResult implements Serializable {
	private Integer templateId;

	private Integer parentTemplateId;

	public RuleResult(Integer templateId, Integer parentTemplateId) {
		this.templateId = templateId;
		this.parentTemplateId = parentTemplateId;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getParentTemplateId() {
		return parentTemplateId;
	}

	public void setParentTemplateId(Integer parentTemplateId) {
		this.parentTemplateId = parentTemplateId;
	}

	@Override public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("  \"parentTemplateId\":").append(parentTemplateId);
		sb.append(", \"templateId\":").append(templateId);
		sb.append('}');
		return sb.toString();
	}
}
