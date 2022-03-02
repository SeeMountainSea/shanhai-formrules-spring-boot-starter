package com.wangshanhai.formrules.component;

import com.wangshanhai.formrules.annotation.RequestFormRules;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * 规则校验器
 */
@Aspect
@Configuration
public class FormRulesComponent {
    @Autowired
    private RulesAnalyseComponent rulesAnalyseComponent;

    @Pointcut("@annotation(com.wangshanhai.formrules.annotation.RequestFormRules)")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void checkRequestForm(JoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequestFormRules formRulesDef = method.getAnnotation(RequestFormRules.class);
        if(formRulesDef.enable()){
            rulesAnalyseComponent.checkRules(signature,joinPoint,formRulesDef);
        }

    }
}
