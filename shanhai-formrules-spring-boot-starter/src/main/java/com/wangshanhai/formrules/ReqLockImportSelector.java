package com.wangshanhai.formrules;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 自定义注解扫描的自动配置组件
 */
public class ReqLockImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{
                "com.wangshanhai.formrules.component.ReqLockComponent",
                "com.wangshanhai.formrules.config.ReqLockConfig",
                "com.wangshanhai.formrules.service.impl.RedissonRedLock"
        };
    }
}
