package es.gtorresdev.traderstudy.utils;

import org.reflections.Reflections;
import java.util.Set;

public class ClassFinder {
    public static Set<Class<?>> findClassesImplementingInterface(String packageName, Class<?> interfaceClass) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getSubTypesOf((Class<Object>) interfaceClass);
    }
}
