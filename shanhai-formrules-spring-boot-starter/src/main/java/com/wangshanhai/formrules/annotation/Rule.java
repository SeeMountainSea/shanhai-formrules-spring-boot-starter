package com.wangshanhai.formrules.annotation;

import java.lang.annotation.*;

/**
 * @author Shmily
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Rule {
    /**
     * 规则类型
     * @return
     */
    String  ruleType();

    /**
     * 正则表达式
     * @return
     */
    String regExp() default "";

    /**
     * 字符串最大长度
     * @return
     */
    int max() default -1;

    /**
     * 字符串最小长度
     * @return
     */
    int min() default -1;

    /**
     * 字符串固定长度
     * @return
     */
    int len() default -1;

    /**
     * 枚举校验
     * @return
     */
    String [] enums() default {};
    /**
     * 规则作用字段
     * @return
     */
    String  scanFields();
    /**
     * 异常编码
     * @return
     */
    int  errorCode();
    /**
     * 异常消息提示
     * @return
     */
    String  message();
}
