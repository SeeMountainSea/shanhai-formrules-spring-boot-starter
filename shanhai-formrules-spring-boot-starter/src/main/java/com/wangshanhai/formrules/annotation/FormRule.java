package com.wangshanhai.formrules.annotation;

import java.lang.annotation.*;

/**
 * @author Shmily
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FormRule {
    /**
     * 是否启用
     * @return
     */
    boolean enable() default true;
    /**
     * 校验对象(方法参数的参数名，多个使用,分隔)
     * @return
     */
    String  target();

    /**
     * 规则集合
     * @return
     */
    Rule [] rules();
}
