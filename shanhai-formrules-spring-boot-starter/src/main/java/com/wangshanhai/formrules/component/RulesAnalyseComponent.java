package com.wangshanhai.formrules.component;

import com.wangshanhai.formrules.annotation.FormRule;
import com.wangshanhai.formrules.annotation.Rule;
import com.wangshanhai.formrules.annotation.ShanHaiForm;
import com.wangshanhai.formrules.service.RuleScanService;
import com.wangshanhai.formrules.service.impl.RuleScanServiceFactory;
import com.wangshanhai.formrules.utils.Logger;
import com.wangshanhai.formrules.utils.ObjectUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 规则分析组件
 */
@Component
public class RulesAnalyseComponent {
    @Autowired
    private RuleScanServiceFactory ruleScanServiceFactory;

    /**
     * 解析规则
     *
     * @param signature    方法定义
     * @param joinPoint    AOP对象
     * @param formRulesDef 规则定义
     */
    public void checkRules(MethodSignature signature, JoinPoint joinPoint, ShanHaiForm formRulesDef) {

        //获取变量名列表
        String[] reqParams = signature.getParameterNames();
        //获取变量对应的类型
        Class[] reqClasses = signature.getParameterTypes();
        //获取全量参数
        Object[] params = joinPoint.getArgs();
        FormRule[] ruleScope = formRulesDef.value();
        if (ruleScope.length > 0) {
            for (FormRule targetForm : ruleScope) {
                if(!targetForm.enable()){
                    continue;
                }
                String target = targetForm.target();
                if (!StringUtils.isEmpty(target)) {
                    int checkIndex = getParamsIndex(reqParams, target);
                    if (checkIndex > -1) {
                        Rule[] rules = targetForm.rules();
                        for (Rule rule : rules) {
                            String ruleType = rule.ruleType();
                            String scanFields = rule.scanFields();
                            if (StringUtils.isEmpty(ruleType) || StringUtils.isEmpty(scanFields)) {
                                Logger.error("[shanhai-form-rules-alert]-method:{},target:{}, From definition is error,RuleType and scanField must define!",
                                        signature.getMethod().getName(), target);
                            } else {
                                List<String> scanFieldList = Arrays.asList(scanFields.split(","));
                                RuleScanService ruleScanService = ruleScanServiceFactory.getRuleScanService(ruleType);
                                if (ruleScanService != null) {
                                    scanFieldList = fixScanFields(params[checkIndex], scanFieldList);
                                    ruleScanService.scanByScope(params[checkIndex], scanFieldList, signature.getMethod()+"->"+target,rule);
                                } else {
                                    Logger.error("[shanhai-form-rules-alert]-method:{},ruleType:{} can not find!",
                                            signature.getMethod().getName(), ruleType);
                                }
                            }
                        }
                    } else {
                        Logger.error("[shanhai-form-rules-alert]-method:{},target:{} can not find!",
                                signature.getMethod().getName(), target);
                    }
                } else {
                    Logger.error("[shanhai-form-rules-alert]-method:{},target is not empty!",
                            signature.getMethod().getName());
                }

            }
        }
    }

    /**
     * 获取变量位置
     *
     * @param reqParams   全量参数名
     * @param targetParam 目标参数名
     * @return
     */
    public int getParamsIndex(String[] reqParams, String targetParam) {
        for (int i = 0; i < reqParams.length; i++) {
            if (targetParam.equals(reqParams[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 二次确认扫描字段
     *
     * @param target     目标对象
     * @param scanFields 配置字段
     * @return
     */
    public List<String> fixScanFields(Object target, List<String> scanFields) {
        List<String> fields = ObjectUtils.getFiledName(target);
        List<String> checkfields = new ArrayList<>();
        for (String s : scanFields) {
            if ("*".equals(s)) {
                checkfields = new ArrayList<>();
                checkfields.addAll(fields);
                break;
            } else {
                if (fields.contains(s)) {
                    checkfields.add(s);
                }
            }
        }
        return checkfields;
    }
}
