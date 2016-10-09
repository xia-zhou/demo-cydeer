package com.cydeer.demo.groovy.impl;

import com.cydeer.demo.groovy.api.RuleScriptEntityRepo;
import com.cydeer.demo.groovy.entity.RuleScriptEntity;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangsong on 16/10/9.
 */
@Repository("ruleScriptEntityRepo")
public class RuleScriptEntityRepoImpl implements RuleScriptEntityRepo {

	private volatile Map<Integer, RuleScriptEntity> data = new ConcurrentHashMap<>();

	@Override public RuleScriptEntity getByCityId(Integer cityId) {
		RuleScriptEntity ruleScriptEntity = new RuleScriptEntity();
		ruleScriptEntity.setCityId(1);
		ruleScriptEntity.setLastModifyTime(new Date());
		ruleScriptEntity.setScript("package com.cydeer.demo.groovy.impl\n"
				+ "\n"
				+ "import com.cydeer.demo.groovy.api.SettleRule\n"
				+ "import com.cydeer.demo.groovy.dto.RuleParam\n"
				+ "import com.cydeer.demo.groovy.dto.RuleResult\n"
				+ "\n"
				+ "/**\n"
				+ " * Created by zhangsong on 16/10/9.\n"
				+ " */\n"
				+ "class SimpleRule implements SettleRule {\n"
				+ "\n"
				+ "    private static final Integer businessId_324 = 324;\n"
				+ "    private static final Integer businessId_325 = 325;\n"
				+ "    private static final Integer businessId_326 = 326;\n"
				+ "    private static final Integer businessId_327 = 327;\n"
				+ "\n"
				+ "    @Override\n"
				+ "    RuleResult run(RuleParam param) {\n"
				+ "        println(param.toString());\n"
				+ "        Integer templateId = null;\n"
				+ "        Integer parentTemplateId = null;\n"
				+ "        if (businessId_324.equals(param.getBusinessId())) {\n"
				+ "            templateId = 11;\n"
				+ "        } else if (businessId_325.equals(param.getBusinessId())) {\n"
				+ "            templateId = 12;\n"
				+ "        } else if (businessId_326.equals(param.getBusinessId())) {\n"
				+ "            templateId = 13;\n"
				+ "        } else if (businessId_327.equals(param.getBusinessId())) {\n"
				+ "            templateId = 14;\n"
				+ "        }\n"
				+ "        Integer tempTemplateId;\n"
				+ "        if (Integer.valueOf(1).equals(param.getCareShopLevel())) {\n"
				+ "            if (Integer.valueOf(1).equals(param.getStmtType())) {\n"
				+ "                tempTemplateId = 2;\n"
				+ "            } else {\n"
				+ "                tempTemplateId = 4;\n"
				+ "            }\n"
				+ "        } else {\n"
				+ "            if (Integer.valueOf(1).equals(param.getStmtType())) {\n"
				+ "                tempTemplateId = 1;\n"
				+ "            } else {\n"
				+ "                tempTemplateId = 3;\n"
				+ "            }\n"
				+ "        }\n"
				+ "        if (templateId == null) {\n"
				+ "            templateId = tempTemplateId\n"
				+ "        } else {\n"
				+ "            parentTemplateId = tempTemplateId;\n"
				+ "        }\n"
				+ "        RuleResult result = new RuleResult(templateId, parentTemplateId);\n"
				+ "        print(result.toString());\n"
				+ "        return result\n"
				+ "    }\n"
				+ "}");
		ruleScriptEntity.setScriptName("SimpleSettleRule");
		data.put(1, ruleScriptEntity);
		return data.get(cityId);
	}

}
