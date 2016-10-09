package com.cydeer.demo.groovy.api;

import com.cydeer.demo.groovy.dto.RuleParam;
import com.cydeer.demo.groovy.dto.RuleResult;

/**
 * Created by zhangsong on 16/10/9.
 * 所有脚本的接口类
 */
public interface SettleRule {
	/**
	 * @param param 所需参数
	 * @return 执行结果
	 */
	RuleResult run(RuleParam param);
}
