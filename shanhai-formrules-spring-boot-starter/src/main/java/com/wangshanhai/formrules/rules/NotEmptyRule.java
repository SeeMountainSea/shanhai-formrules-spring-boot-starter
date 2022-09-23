package com.wangshanhai.formrules.rules;

import com.wangshanhai.formrules.service.RuleScanService;
import com.wangshanhai.formrules.utils.HttpFormRulesException;
import com.wangshanhai.formrules.utils.ObjectUtils;
import com.wangshanhai.formrules.utils.RuleCollect;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotEmptyRule implements RuleScanService {
    @Override
    public String getScanType() {
        return RuleCollect.NOT_EMPTY;
    }

    @Override
    public void scanByScope(Object target, List<String> scanFields, Integer errorCode, String message) {
        List<String> fields= ObjectUtils.getFiledName(target);
        List<String> checkfields=new ArrayList<>();
        for(String s:scanFields){
            if("*".equals(s)){
                checkfields=new ArrayList<>();
                checkfields.addAll(fields);
                break;
            }else{
                if(fields.contains(s)){
                    checkfields.add(s);
                }
            }
        }
        for(String field:checkfields){
            if(StringUtils.isEmpty(ObjectUtils.getFieldValueByName(target,field))){
                throw new HttpFormRulesException(errorCode,message+"@"+field);
            }
        }
    }
}
