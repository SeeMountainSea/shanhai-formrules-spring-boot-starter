package com.wangshanhai.formrules.service.impl;

import com.wangshanhai.formrules.service.RuleScanService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RuleScanServiceFactory implements ApplicationContextAware {
    private final Map<String, RuleScanService> bizServices=new HashMap<String,RuleScanService>();
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String,RuleScanService> facePushBizServiceMap=applicationContext.getBeansOfType(RuleScanService.class);
        facePushBizServiceMap.values().forEach(e->{
            bizServices.put(e.getScanType(),e);
        });
    }
    public RuleScanService getRuleScanService(String scanType){
        return bizServices.get(scanType);
    }
}