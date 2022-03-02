package com.wangshanhai.formrules.component;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.spring.SpringContextFunctionLoader;
import com.wangshanhai.formrules.utils.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 规则注册
 */
@Component
public class RulesRegisterComponent implements InitializingBean {
    @Autowired
    private ApplicationContext context;
    @Override
    public void afterPropertiesSet() throws Exception {
        AviatorEvaluator.addFunctionLoader(new SpringContextFunctionLoader(context));
        Logger.info("[Form-Rules]-Init");
    }
}
