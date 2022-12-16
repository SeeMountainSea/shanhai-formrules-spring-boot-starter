package com.wangshanhai.formrules.annotation;

import com.wangshanhai.formrules.SqlLockImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用Form规则校验引擎
 * @author Shmily
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SqlLockImportSelector.class)
public @interface EnableShanHiSqlLock {
}
