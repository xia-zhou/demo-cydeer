package com.cydeer.demo.groovy.impl;

import com.cydeer.demo.groovy.api.RuleScriptEntityRepo;
import com.cydeer.demo.groovy.api.SettleRule;
import com.cydeer.demo.groovy.dto.RuleParam;
import com.cydeer.demo.groovy.dto.RuleResult;
import com.cydeer.demo.groovy.dto.RuleScriptBean;
import com.cydeer.demo.groovy.entity.RuleScriptEntity;
import groovy.lang.GroovyClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangsong on 16/10/9.
 * <p>
 * 脚本执行者,包括了初始化,和最终执行
 */
@Service
public class RuleExecutor {

	private final static Logger LOGGER = LoggerFactory.getLogger(RuleExecutor.class);

	@Resource
	private RuleScriptEntityRepo ruleScriptEntityRepo;

	/**
	 * 缓存 各个条件的脚本
	 */
	private volatile static Map<Integer, RuleScriptBean> ruleScriptBeans = new ConcurrentHashMap<>();

	/**
	 * @param cityId 脚本的获取标识
	 * @param param  需要的参数
	 * @return 执行结果
	 */
	public RuleResult execute(Integer cityId, RuleParam param) {
		initRuleScriptBeans(cityId);
		RuleScriptBean ruleScriptBean = ruleScriptBeans.get(cityId);
		if (ruleScriptBean != null) {
			try {
				return ruleScriptBean.getClazz().newInstance().run(param);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 初始化该城市的脚本信息
	 *
	 * @param cityId
	 */
	private RuleScriptBean initRuleScriptBeans(Integer cityId) {
		RuleScriptBean ruleScriptBean = ruleScriptBeans.get(cityId);
		RuleScriptEntity ruleScriptEntity = ruleScriptEntityRepo.getByCityId(cityId);
		if (ruleScriptEntity == null) {
			LOGGER.info("对应的城市的脚本不存在:{}", cityId);
			return null;
		}
		if (ruleScriptBean == null || ruleScriptBean.getLastModifyTime().getTime() != ruleScriptBean.getLastModifyTime()
		                                                                                            .getTime()) {
			ruleScriptBean = new RuleScriptBean(
					loadScriptClass(ruleScriptEntity.getScript(), ruleScriptEntity.getScriptName()),
					ruleScriptEntity.getLastModifyTime());
			ruleScriptBeans.put(cityId, ruleScriptBean);
		}
		return ruleScriptBean;
	}

	/**
	 * @param script     脚本
	 * @param scriptName 脚本名字
	 * @return 加载后的脚本class
	 */
	private Class<SettleRule> loadScriptClass(String script, String scriptName) {
		GroovyClassLoader groovyClassLoader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader());
		Class<SettleRule> clazz = groovyClassLoader.parseClass(script, scriptName);
		return clazz;
	}

}
