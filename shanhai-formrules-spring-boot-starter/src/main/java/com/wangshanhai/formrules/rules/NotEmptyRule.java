package com.wangshanhai.formrules.rules;

import com.wangshanhai.formrules.annotation.Rule;
import com.wangshanhai.formrules.service.RuleScanService;
import com.wangshanhai.formrules.exception.HttpFormRulesException;
import com.wangshanhai.formrules.utils.ObjectUtils;
import com.wangshanhai.formrules.utils.RuleCollect;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 非空校验
 */
@Service
public class NotEmptyRule implements RuleScanService {
    @Override
    public String getScanType() {
        return RuleCollect.NOT_EMPTY;
    }

    @Override
    public void scanByScope(Object target, List<String> scanFields,String execTarget, Rule rule) {
        for(String field:scanFields){
            Object value= ObjectUtils.getFieldValueByName(target,field);
            if(value==null||StringUtils.isEmpty(String.valueOf(value))){
                throw new HttpFormRulesException(rule.errorCode(),field+":"+rule.message());
            }
        }
    }
}
