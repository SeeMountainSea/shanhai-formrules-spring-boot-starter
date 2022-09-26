package com.wangshanhai.formrules.rules;

import com.wangshanhai.formrules.annotation.Rule;
import com.wangshanhai.formrules.service.RuleScanService;
import com.wangshanhai.formrules.utils.HttpFormRulesException;
import com.wangshanhai.formrules.utils.Logger;
import com.wangshanhai.formrules.utils.ObjectUtils;
import com.wangshanhai.formrules.utils.RuleCollect;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 正则校验
 */
@Service
public class RegExpRule implements RuleScanService {
    @Override
    public String getScanType() {
        return RuleCollect.REG_EXP;
    }

    @Override
    public void scanByScope(Object target, List<String> scanFields,String execTarget, Rule rule) {
        for(String field:scanFields){
            Object value= ObjectUtils.getFieldValueByName(target,field);
            String waitValue=String.valueOf(value);
            if(!StringUtils.isEmpty(rule.regExp())){
                if(!Pattern.matches(rule.regExp() , waitValue)){
                    throw new HttpFormRulesException(rule.errorCode(),field+":"+rule.message());
                }
            }else{
                Logger.error("[shanhai-form-rules-alert]-execTarget:{},regExp Str can not find!",execTarget,
                        rule.ruleType());
            }
        }
    }
}
