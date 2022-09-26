package com.wangshanhai.formrules.service;

import com.wangshanhai.formrules.annotation.Rule;

import java.util.List;

public interface RuleScanService {
    /**
     * 注册自己的规则定义
     * @return
     */
    public String getScanType();
    /**
     *扫描目标对象
     * @param target 目标对象实例
     * @param scanFields 目标对象扫描字段
     * @param execTarget 目标方法信息（跟踪日志使用）
     * @param rule 规则定义
     */
    public void scanByScope(Object target, List<String> scanFields,String execTarget, Rule rule);
}
