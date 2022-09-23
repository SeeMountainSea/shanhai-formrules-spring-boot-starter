package com.wangshanhai.formrules.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ObjectUtils {
    /**
     * 获取对象的全部字段名
     *
     * @param o o
     * @return {@link String[]}
     */
    public static List<String> getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        List<String> fieldsList = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            if (Modifier.isStatic(fields[i].getModifiers())) {
                continue;
            }
            String fieldType = fields[i].getType().getName();
            if (fieldType.contains(".") && !fieldType.contains("java.lang")) {
                fieldsList.addAll(getChildFiledName(getFieldValueByName(o, fields[i].getName()), fields[i].getName()));
            } else {
                fieldsList.add(fields[i].getName());
            }
        }
        return fieldsList;
    }

    /**
     * 递归查询对象字段
     *
     * @param o         目标对象
     * @param classFlag 对象上游字段标识
     * @return
     */
    public static List<String> getChildFiledName(Object o, String classFlag) {
        if(o==null){
            return new ArrayList<>();
        }
        Field[] fields = o.getClass().getDeclaredFields();
        List<String> fieldsList = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            if (Modifier.isStatic(fields[i].getModifiers())) {
                continue;
            }
            String fieldType = fields[i].getType().getName();
            if (fieldType.contains(".") && !fieldType.contains("java.")) {
                Object next=getFieldValueByName(o, fields[i].getName());
                String nextFlag=classFlag+"."+fields[i].getName();
                fieldsList.addAll(getChildFiledName(next,nextFlag ));
            } else {
                fieldsList.add(classFlag + "." + fields[i].getName());
            }

        }
        return fieldsList;
    }

    /**
     * 获取某个对象某个字段的值（支持嵌套字段查询）
     *
     * @param o         目标对象
     * @param fieldName 字段名
     * @return {@link Object}
     */
    public static Object getFieldValueByName(Object o, String fieldName) {
        if(o==null){
            return null;
        }
        try {
            if (fieldName.contains(".")) {
                List<String> methodLinks = Arrays.asList(fieldName.split("\\."));
                if (methodLinks.size() > 1) {
                    Object next=getSingleFieldValueByName(o, methodLinks.get(0));
                    return getFieldValueByName(next, String.join(".", methodLinks.subList(1,methodLinks.size())));
                }
            } else {
               return getSingleFieldValueByName(o, fieldName);
            }
        } catch (Exception e) {
            Logger.error("获取属性值失败！,msg:{}", e);
        }
        return null;
    }

    /**
     * 获取某个对象某个字段的值（仅支持一级简单类型）
     * @param o
     * @param fieldName
     * @return
     */
    public static Object getSingleFieldValueByName(Object o, String fieldName) {
        if(o==null){
            return null;
        }
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {
            Logger.error("获取属性值失败！,msg:{}", e);
        }
        return null;
    }
    /**
     * 获取对象某个字段的数据类型
     *
     * @param o
     * @param fieldName
     * @return
     */
    public static Object getFiledType(Object o, String fieldName) {
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (Objects.equals(fieldName, field.getName())) {
                return field.getType();
            }
        }
        return null;
    }


}
