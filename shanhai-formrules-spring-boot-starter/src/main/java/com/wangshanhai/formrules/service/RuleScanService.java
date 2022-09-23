package com.wangshanhai.formrules.service;

import java.util.List;

public interface RuleScanService {
    public String getScanType();
    /**
     *扫描目标对象
     * @param target 目标对象实例
     * @param fieldsScope 目标对象扫描字段
     */
    public void scanByScope(Object target, List<String> scanFields,Integer errorCode,String message);
}
