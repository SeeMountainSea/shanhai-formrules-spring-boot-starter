package com.wangshanhai.formrules.rules;

import com.wangshanhai.formrules.annotation.Rule;
import com.wangshanhai.formrules.service.RuleScanService;
import com.wangshanhai.formrules.exception.HttpFormRulesException;
import com.wangshanhai.formrules.utils.ObjectUtils;
import com.wangshanhai.formrules.utils.RuleCollect;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字符串长度校验
 */
@Service
public class StrLengthRule implements RuleScanService {
    @Override
    public String getScanType() {
        return RuleCollect.STR_LENTH;
    }

    @Override
    public void scanByScope(Object target, List<String> scanFields, String execTarget, Rule rule) {
        for(String field:scanFields){
            Object value= ObjectUtils.getFieldValueByName(target,field);
            String waitValue=String.valueOf(value);
            if(rule.max()>0){
                if(waitValue.length()> rule.max()){
                    throw new HttpFormRulesException(rule.errorCode(),field+":"+rule.message());
                }
            }
            if(rule.min()>0){
                if(waitValue.length()< rule.min()){
                    throw new HttpFormRulesException(rule.errorCode(),field+":"+rule.message());
                }
            }
            if(rule.len()>0){
                if(waitValue.length()!=rule.len()){
                    throw new HttpFormRulesException(rule.errorCode(),field+":"+rule.message());
                }
            }
        }
    }
}
