package com.wangshanhai.formrules.annotation;

import java.lang.annotation.*;

/**
 * 校验规则
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestFormRules {
    /**
     * 校验对象
     * @return
     */
   String [] target();
    /**
     * 规则清单(多个使用;分割)
     * @return
     */
    String rule() default "" ;
    /**
     * 是否启用
     * @return
     */
   boolean enable() default true;
}
