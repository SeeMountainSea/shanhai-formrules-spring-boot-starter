package com.wangshanhai.formrules.annotation;

import java.lang.annotation.*;

/**
 * 校验规则
 * @author Shmily
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestFormRules {
    /**
     * 校验对象(方法参数的参数名，多个使用,分隔)
     * @return
     */
   String [] target();
    /**
     * 规则作用域(多个使用,分割)
     * @return
     */
    String  [] ruleScope() default "" ;
    /**
     * 是否启用
     * @return
     */
   boolean enable() default true;
}
