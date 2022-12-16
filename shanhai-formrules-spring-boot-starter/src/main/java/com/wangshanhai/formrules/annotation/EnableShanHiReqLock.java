package com.wangshanhai.formrules.annotation;

import com.wangshanhai.formrules.ReqLockImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用Form规则校验引擎
 * @author Shmily
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ReqLockImportSelector.class)
public @interface EnableShanHiReqLock {
}
