package com.wangshanhai.formrules.func;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorNil;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.wangshanhai.formrules.utils.HttpBizRulesException;
import com.wangshanhai.formrules.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 非空校验
 */
@Service("shf.notEmpty")
public class NotEmptyRules extends AbstractFunction {
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject target, AviatorObject code, AviatorObject desc) {
        Object targetObj= FunctionUtils.getJavaObject(target,env);
        String codeVal= FunctionUtils.getStringValue(code,env);
        String descVal= FunctionUtils.getStringValue(desc,env);
        if(targetObj instanceof String){
            if(StringUtils.isEmpty(String.valueOf(targetObj))){
                throw new HttpBizRulesException(codeVal,descVal);
            }
        }else{ //需要进行全字段匹配
            List<String> fields=ObjectUtils.getFiledName(targetObj);
            for(String field:fields){
                if(StringUtils.isEmpty(ObjectUtils.getFieldValueByName(targetObj,field))){
                    throw new HttpBizRulesException(codeVal,field+":"+descVal);
                }
            }
        }
        return AviatorNil.NIL;
    }
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject target, AviatorObject code,AviatorObject ignore, AviatorObject desc) {
        Object targetObj= FunctionUtils.getJavaObject(target,env);
        String codeVal= FunctionUtils.getStringValue(code,env);
        String descVal= FunctionUtils.getStringValue(desc,env);
        String ignoreVal= FunctionUtils.getStringValue(ignore,env);
        if(targetObj instanceof String){
            if(StringUtils.isEmpty(String.valueOf(targetObj))){
                throw new HttpBizRulesException(codeVal,descVal);
            }
        }else{ //需要进行全字段匹配
            List<String> fields=ObjectUtils.getFiledName(targetObj);
            String [] ignoreList=new String[]{};
            if(!StringUtils.isEmpty(ignoreVal)){
                ignoreVal.split(",");
            }
            for(String field:fields){
                if(ignoreVal.contains(field)){
                    continue;
                }
                if(StringUtils.isEmpty(ObjectUtils.getFieldValueByName(targetObj,field))){
                    throw new HttpBizRulesException(codeVal,field+":"+descVal);
                }
            }
        }
        return AviatorNil.NIL;
    }
    @Override
    public String getName() {
        return "shf.notEmpty";
    }
}
