package com.wangshanhai.formrules.component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangshanhai.formrules.annotation.RequestFormRules;
import com.wangshanhai.formrules.service.RuleDefLoadService;
import com.wangshanhai.formrules.service.RuleScanService;
import com.wangshanhai.formrules.service.impl.RuleScanServiceFactory;
import com.wangshanhai.formrules.utils.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * 规则分析组件
 */
@Component
public class RulesAnalyseComponent {
    @Autowired
    private RuleScanService ruleScanService;
    @Autowired
    private RuleDefLoadService ruleDefLoadService;
    @Autowired
    private RuleScanServiceFactory ruleScanServiceFactory;
    /**
     * 规则定义信息
     */
    public static String ruleDef;

    @PostConstruct
    public void init(){
        String tmp=ruleDefLoadService.loadRuleDef();
        if(StringUtils.isEmpty(tmp)){
            Logger.error("[ShanhaiForm]-load rule error");
        }else{
            ruleDef= tmp;
        }
    }
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
        String [] ruleScope=formRulesDef.ruleScope();
        if(ruleScope.length>0){
            String [] targets=formRulesDef.target();
            if(targets.length>0 && ruleScope.length==targets.length){
                for(String target:targets){
                    int checkIndex=getParamsIndex(reqParams,target);
                    if(checkIndex>-1){
                        JSONObject resObj=JSONObject.parseObject(ruleDef);
                        JSONArray ruleDefList = resObj.getJSONArray("ruleDefList");
                        JSONArray ruleScanList =new JSONArray();
                        for(int i=0;i<ruleDefList.size();i++){
                            JSONObject t=ruleDefList.getJSONObject(i);
                            if(ruleScope[checkIndex].equals(t.getString("scope"))){
                                ruleScanList=t.getJSONArray("scanRules");
                                break;
                            }
                        }
                        for(int i=0;i<ruleScanList.size();i++){
                            JSONObject t=ruleScanList.getJSONObject(i);
                            String scanType=t.getString("ruleType");
                            List<String> scanFields= Arrays.asList(t.getString("scanFields").split(","));
                            RuleScanService ruleScanService=ruleScanServiceFactory.getRuleScanService(scanType);
                            if(ruleScanService!=null){
                                ruleScanService.scanByScope(params[checkIndex],scanFields,t.getInteger("errorCode"),t.getString("message"));
                            }else{
                                Logger.error("[shanhai-form-rules-alert]-method:{},rule:{} is not exist!",
                                        signature.getMethod().getName(),scanType);
                            }

                        }

                    }else{
                        Logger.error("[shanhai-form-rules-alert]-method:{},rulesTarget:{} is not exist!",
                                signature.getMethod().getName(),target);
                    }
                }
            }else{
                Logger.error("[shanhai-form-rules-exec]-method:{},msg: ruleScope and targets number must equals ", signature.getMethod().getName());
            }

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
