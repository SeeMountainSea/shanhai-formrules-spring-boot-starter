package com.shanhai.examples.api;

import com.shanhai.examples.form.RulesForm;
import com.wangshanhai.formrules.annotation.FormRule;
import com.wangshanhai.formrules.annotation.Rule;
import com.wangshanhai.formrules.annotation.ShanHaiForm;
import com.wangshanhai.formrules.utils.RuleCollect;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RulesController {
    /**
     * 规则测试
     * @param rulesForm
     * @return
     */
    @RequestMapping("/restRules")
    @ShanHaiForm({
            @FormRule(target = "rulesForm",rules = {
                    @Rule(ruleType = RuleCollect.NOT_EMPTY,scanFields = "*",errorCode = 1001,message = "参数不能为空！"),
//                    @Rule(ruleType = RuleCollect.REG_EXP,scanFields = "projectType", regExp = RegExpCollect.NUMBER,errorCode = 1002,message = "不符合格式要求！"),
                    @Rule(ruleType = RuleCollect.ENUM,scanFields = "projectType", enums = {"张三","李四"},errorCode = 1002,message = "不符合格式要求！"),
                    @Rule(ruleType = RuleCollect.STR_LENTH,scanFields = "projectType", min = 1,max = 30,errorCode = 1002,message = "不符合长度要求！")
            })
    })
    public String restRules(@RequestBody RulesForm rulesForm) {
        return "success";
    }

}
