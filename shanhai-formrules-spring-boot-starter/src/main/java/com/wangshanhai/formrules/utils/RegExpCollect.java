package com.wangshanhai.formrules.utils;

/**
 * 默认规则集合
 * @author Shmily
 */
public class RegExpCollect {
    /**
     * 中文校验
     */
    public static final String CN = "^[\\u4e00-\\u9fa5]{0,}$";
    /**
     * 数字校验
     */
    public static final String NUMBER="^(\\-|\\+)?\\d+(\\.\\d+)?$";
    /**
     * IP地址
     */
    public static final String IP="\\d+\\.\\d+\\.\\d+\\.\\d+";
    /**
     * 邮政编码
     */
    public static final String POSTAL="[1-9]\\d{5}(?!\\d)";
    /**
     * 身份证号
     */
    public static final String ID_CARD="^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$";
    /**
     * 手机号
     */
    public static final String PHONE="^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";
    /**
     * 邮箱
     */
    public static final String EMAIL="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /**
     * 网址
     */
    public static final String INTERNET="[a-zA-z]+://[^\\s]*";
    /**
     * 统一社会信用代码
     */
    public static final String ORG_CODE="^([0-9A-HJ-NPQRTUWXY]{2}\\d{6}[0-9A-HJ-NPQRTUWXY]{10}|[1-9]\\d{14})$";
}