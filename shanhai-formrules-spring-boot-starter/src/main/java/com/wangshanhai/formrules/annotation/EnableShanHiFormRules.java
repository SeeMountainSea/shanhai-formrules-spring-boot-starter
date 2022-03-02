package com.wangshanhai.formrules.annotation;

import com.wangshanhai.formrules.FormRulesImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用Form规则校验引擎
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(FormRulesImportSelector.class)
public @interface EnableShanHiFormRules {
}
