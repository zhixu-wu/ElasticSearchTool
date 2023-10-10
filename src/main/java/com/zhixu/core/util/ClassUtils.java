package com.zhixu.core.util;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;

/**
 * @author wuzhixu
 * @date 2023/6/19
 * 描述:
 */
public class ClassUtils {


    /**
     * 获取子类上某个注解
     *
     * @param parentClass
     * @param annotate
     * @param <T>
     * @param <A>
     * @return
     */
    public static <T, A> A findChildAnnotate(Class<? extends T> parentClass, Class<A> annotate) throws IOException, ClassNotFoundException {
        List<Class<? extends T>> list = getSubClasses(parentClass);

        if (list.size() == 0) {
            return null;
        }

        for (Class clazz : list) {
            Annotation[] array = clazz.getAnnotations();

            for (int a = 0; a < array.length; a++) {

                if (array[a].annotationType().equals(annotate)) {
                    return (A) array[a];
                }
            }
        }

        return null;
    }

    /**
     * 获取某个类上某个注解
     * @param clazz
     * @param annotate
     * @return
     * @param <T>
     * @param <A>
     */
    public static <T,A> A getAnnotate(Class<T> clazz, Class<A> annotate){
        Annotation[] array = clazz.getAnnotations();

        for (int a = 0; a < array.length; a++) {

            if (array[a].annotationType().equals(annotate)) {
                return (A) array[a];
            }
        }

        return null;
    }

    /**
     * 获取子类
     * @param parentClass
     * @return
     * @param <T>
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> List<Class<? extends T>> getSubClasses(Class<? extends T> parentClass) throws IOException, ClassNotFoundException {
        List<Class<? extends T>> subClasses = new ArrayList<>();
        Package[] packages = Package.getPackages();
        for (Package pkg : packages) {
            String packageName = pkg.getName();
            List<Class<?>> classes = find(packageName);
            for (Class<?> clazz : classes) {
                if (parentClass.isAssignableFrom(clazz) && !parentClass.equals(clazz)) {
                    subClasses.add((Class<? extends T>) clazz);
                }
            }
        }
        return subClasses;
    }


    public static List<Class<?>> find(String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }


    public static Class getGenericSuperclass(Class clazz) {
        Type superClass = clazz.getGenericSuperclass();
        // 判断是否是 ParameterizedType
        if (superClass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) superClass;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            // 假设泛型参数只有一个，取第一个
            if (typeArguments.length > 0) {
                if (typeArguments[0] instanceof Class) {
                    return (Class) typeArguments[0];
                }
            }
        }

        return null;
    }


    private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }


    public static Set<String> allFields(Class<?> clazz) {
        Set<String> rSet = new HashSet<>();

        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                rSet.add(field.getName());
            }
            clazz = clazz.getSuperclass();
        }

        return rSet;
    }


    public static <T extends Annotation> T getFieldAnnotation(Class<?> clazz, String fieldName, Class<T> annotationClass) {
        Field field = getField(clazz, fieldName);
        if (field != null) {
            return field.getAnnotation(annotationClass);
        }
        return null;
    }

    private static Field getField(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (NoSuchFieldException e) {
                // Field not found in current class, continue to search in the superclass
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }


}
