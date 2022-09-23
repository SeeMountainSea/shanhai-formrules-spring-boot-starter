package com.wangshanhai.formrules.service.impl;


import com.wangshanhai.formrules.service.RuleDefLoadService;
import org.aspectj.util.FileUtil;
import org.springframework.util.ResourceUtils;

import java.io.File;

public class RuleDefLoadServiceImpl implements RuleDefLoadService {
    @Override
    public String loadRuleDef() {
        try{
            File file = ResourceUtils.getFile("classpath:shanhaiforms.json");
           return FileUtil.readAsString(file);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
