package com.wangshanhai.formrules.annotation;

import java.lang.annotation.*;

/**
 * 规则集合定义
 * @author Shmily
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ShanHaiForm {
    /**
     * 规则集合
     * @return
     */
    FormRule [] value();
}
