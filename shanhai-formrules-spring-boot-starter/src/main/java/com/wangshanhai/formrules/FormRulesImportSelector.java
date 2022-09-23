package com.wangshanhai.formrules;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 自定义注解扫描的自动配置组件
 */
public class FormRulesImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
                "com.wangshanhai.formrules.component.FormRulesComponent",
                "com.wangshanhai.formrules.component.RulesAnalyseComponent",
                "com.wangshanhai.formrules.service.impl.RuleScanServiceFactory",
                "com.wangshanhai.formrules.rules.NotEmptyRule",
        };
    }
}
