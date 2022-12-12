package com.wangshanhai.formrules.rules;

import com.wangshanhai.formrules.annotation.Rule;
import com.wangshanhai.formrules.service.RuleScanService;
import com.wangshanhai.formrules.exception.HttpFormRulesException;
import com.wangshanhai.formrules.utils.ObjectUtils;
import com.wangshanhai.formrules.utils.RuleCollect;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 枚举校验
 */
@Service
public class EnumsRule implements RuleScanService {
    @Override
    public String getScanType() {
        return RuleCollect.ENUM;
    }

    @Override
    public void scanByScope(Object target, List<String> scanFields, String execTarget, Rule rule) {
        for(String field:scanFields){
            Object value= ObjectUtils.getFieldValueByName(target,field);
            String waitValue=String.valueOf(value);
            if(rule.enums().length>0){
                List enumList=Arrays.asList(rule.enums());
                if(!enumList.contains(waitValue)){
                    throw new HttpFormRulesException(rule.errorCode(),field+":"+rule.message());
                }
            }
        }
    }
}
