package com.wangshanhai.formrules.utils;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObjectUtils {
    /**
     * 获取对象的全部字段名
     * @param o o
     * @return {@link String[]}
     */
    public static    List<String> getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        List<String> fieldsList=new ArrayList<>();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            if( Modifier.isStatic(fields[i].getModifiers())) {
                continue;
            }
            fieldsList.add(fields[i].getName());
        }
        return fieldsList;
    }

    /**
     * 获取对象某个字段的值
     * @param o         o
     * @param fieldName 字段名
     * @return {@link Object}
     */
    public static Object getFieldValueByName( Object o,String fieldName) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            Logger.error("获取属性值失败！,msg:{}", e);
        }
        return null;
    }

    /**
     * 获取对象某个字段的数据类型
     * @param o
     * @param fieldName
     * @return
     */
    public static Object getFiledType(Object o,String fieldName) {
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (Objects.equals(fieldName, field.getName())) {
                return field.getType();
            }
        }
        return null;
    }

}
