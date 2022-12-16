package com.wangshanhai.formrules.annotation;

import java.lang.annotation.*;

/**
 * SQL锁
 *
 * @author Fly.Sky
 * @since 2022/12/14 13:49
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SqlLock {
    /**
     * SQL表达式
     * @return
     */
    String expression();

    /**
     * SQL锁条件赋值字段
     * @return
     */
    String [] expressionValueTargets() default {};

    /**
     * 是否启用缓存(默认：启用)
     * @return
     */
    boolean cache() default true;

    /**
     * 缓存过期时间(默认：180s)
     * @return
     */
    long    cacheExpire() default 180;
}
