package com.imooc.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

/**
 * 复制properties，解决了BeanUtils的复制方法中，null会覆盖原有值的问题。
 * 版权声明：本文为CSDN博主「山间明月江上清风_」的原创文章，遵循 CC 4.0 BY-SA 版权协议，
 * 转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/u011870280/article/details/80079477
 *
 * @author Zhang Pengnian
 * @since 2019-10-15 18:49
 */
public class CopyUtils {

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copyProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

}