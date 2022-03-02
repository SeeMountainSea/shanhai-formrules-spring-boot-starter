package com.wangshanhai.formrules.component;

import com.googlecode.aviator.AviatorEvaluator;
import com.wangshanhai.formrules.annotation.RequestFormRules;
import com.wangshanhai.formrules.utils.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 规则分析组件
 */
@Component
public class RulesAnalyseComponent {
    /**
     * 解析规则
     * @param signature 方法定义
     * @param joinPoint AOP对象
     * @param formRulesDef 规则定义
     */
    public void checkRules(MethodSignature signature, JoinPoint joinPoint, RequestFormRules formRulesDef){
        //获取变量名列表
        String[] reqParams=signature.getParameterNames();
        //获取变量对应的类型
        Class[] reqClasses=signature.getParameterTypes();
        //获取全量参数
        Object [] params = joinPoint.getArgs();
        if(!StringUtils.isEmpty(formRulesDef.rule())){
            String [] targets=formRulesDef.target();
            Map<String,Object> env=new HashMap<>();
            StringBuffer buildFormat=new StringBuffer();
            for(String target:targets){
                int checkIndex=getParamsIndex(reqParams,target);
                if(checkIndex>-1){
                    env.put(target,params[checkIndex]);
                    String [] formRulesDefs=formRulesDef.rule().split(";");
                    for(String r:formRulesDefs){
                        String t=DigestUtils.md5DigestAsHex(r.getBytes(StandardCharsets.UTF_8));
                        buildFormat.append("let rule").append(t).append("=").append(r).append(";");
                    }
                }else{
                    Logger.error("[shanhai-form-rules-alert]-method:{},rulesTarget:{} is not exist!",
                            signature.getMethod().getName(),formRulesDef.target());
                }
            }
            Logger.info("[shanhai-form-rules-exec]-method:{},rulesTarget:{},script:{}",
                    signature.getMethod().getName(),formRulesDef.target(),buildFormat.toString());
            AviatorEvaluator.execute(buildFormat.toString(),env,true);
        }
    }

    /**
     * 获取变量位置
     * @param reqParams 全量参数名
     * @param targetParam 目标参数名
     * @return
     */
    public int getParamsIndex( String[]reqParams ,String targetParam){
        for(int i=0;i<reqParams.length;i++){
            if(targetParam.equals(reqParams[i])){
                return i;
            }
        }
        return -1;
    }
}
