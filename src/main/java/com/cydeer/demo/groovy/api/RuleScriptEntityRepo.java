package com.cydeer.demo.groovy.api;

import com.cydeer.demo.groovy.entity.RuleScriptEntity;

/**
 * Created by zhangsong on 16/10/9.
 * 数据层,可以根据不同需求,存储不同地方
 */
public interface RuleScriptEntityRepo {

	RuleScriptEntity getByCityId(Integer cityId);

}
