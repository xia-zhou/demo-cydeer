package com.cydeer.demo.groovy.dto;

import java.io.Serializable;

/**
 * Created by zhangsong on 16/10/9.
 * 输入,具体的输入看具体的需求
 */
public class RuleParam implements Serializable {

	private Integer careShopLevel;

	private Integer stmtType;

	private Integer businessId;

	public RuleParam(Integer careShopLevel, Integer stmtType, Integer businessId) {
		this.careShopLevel = careShopLevel;
		this.stmtType = stmtType;
		this.businessId = businessId;
	}

	public Integer getCareShopLevel() {
		return careShopLevel;
	}

	public void setCareShopLevel(Integer careShopLevel) {
		this.careShopLevel = careShopLevel;
	}

	public Integer getStmtType() {
		return stmtType;
	}

	public void setStmtType(Integer stmtType) {
		this.stmtType = stmtType;
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	@Override public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("  \"careShopLevel\":").append(careShopLevel);
		sb.append(", \"stmtType\":").append(stmtType);
		sb.append(", \"businessId\":").append(businessId);
		sb.append('}');
		return sb.toString();
	}
}
