package com.wangshanhai.formrules.annotation;

import java.lang.annotation.*;

/**
 * 分布式锁声明
 * @author Shmily
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReqLock {
    /**
     * 分布式锁分组名
     * @return
     */
    String lockName();
    /**
     * 分布式锁Value生成策略
     * @return
     */
    String lockValueStrategy() default "";

    /**
     * 分布式锁作用域（单一对象或某个对象的某个字段）
     * @return
     */
    String lockTarget() default "";
    /**
     * 分布式锁作用域-多个作用域（每个为单一对象或某个对象的某个字段）
     * @return
     */
    String [] multiLockTarget() default {};
    /**
     * 锁过期时间(单位:秒)
     * @return
     */
    long lockExpireTime() default 120;
    /**
     * 锁获取超时时间 (单位:秒)
     * @return
     */
    long lockTimeOut() default 5;

}
