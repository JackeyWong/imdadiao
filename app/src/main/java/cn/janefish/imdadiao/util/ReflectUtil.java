package cn.janefish.imdadiao.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wangjie on 16/7/18.
 */
public final class ReflectUtil {
    private ReflectUtil() {
    }
    public static List<Field> getAllDeclaredFields(Class clz) {
        List<Field> fieldList = new ArrayList<>();
        Class<?> clazz = clz;
        for (; clazz != null; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            fieldList.addAll(Arrays.asList(fields));
        }
        Class<?>[] ifClazz = clz.getInterfaces();
        for (int i = 0; ifClazz != null && i < ifClazz.length; i++) {
            Field[] fields = ifClazz[i].getDeclaredFields();
            fieldList.addAll(Arrays.asList(fields));
        }
        return fieldList;
    }
}
