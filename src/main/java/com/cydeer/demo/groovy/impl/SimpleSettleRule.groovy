package com.cydeer.demo.groovy.impl

import com.cydeer.demo.groovy.api.SettleRule
import com.cydeer.demo.groovy.dto.RuleParam
import com.cydeer.demo.groovy.dto.RuleResult

/**
 * Created by zhangsong on 16/10/9.
 * 一个 groovy的样例  这个基本不需要,都是 写好的脚本存储起来的。这里只是为了方便查看 业务逻辑也是自己模式的。
 */
class SimpleRule implements SettleRule {

    private static final Integer businessId_324 = 324;
    private static final Integer businessId_325 = 325;
    private static final Integer businessId_326 = 326;
    private static final Integer businessId_327 = 327;

    @Override
    RuleResult run(RuleParam param) {
        println(param.toString());
        Integer templateId = null;
        Integer parentTemplateId = null;
        if (businessId_324.equals(param.getBusinessId())) {
            templateId = 11;
        } else if (businessId_325.equals(param.getBusinessId())) {
            templateId = 12;
        } else if (businessId_326.equals(param.getBusinessId())) {
            templateId = 13;
        } else if (businessId_327.equals(param.getBusinessId())) {
            templateId = 14;
        }
        Integer tempTemplateId;
        if (Integer.valueOf(1).equals(param.getCareShopLevel())) {
            if (Integer.valueOf(1).equals(param.getStmtType())) {
                tempTemplateId = 2;
            } else {
                tempTemplateId = 4;
            }
        } else {
            if (Integer.valueOf(1).equals(param.getStmtType())) {
                tempTemplateId = 1;
            } else {
                tempTemplateId = 3;
            }
        }
        if (templateId == null) {
            templateId = tempTemplateId
        } else {
            parentTemplateId = tempTemplateId;
        }
        RuleResult result = new RuleResult(templateId, parentTemplateId);
        print(result.toString());
        return result
    }
}