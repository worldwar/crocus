package tw.zhuran.crocus.util;

import com.google.common.collect.Lists;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Meta {

    public static Class type(Class c, Object value) {
        List<Class> types = types(c, c.getPackage().getName(), value);
        if (types.size() != 0) {
            return types.get(0);
        }
        return null;
    }

    public static List<Class> types(Class c, Object value) {
        return types(c, c.getPackage().getName(), value);
    }

    public static List<Class> types(Class c) {
        return types(c, c.getPackage().getName());
    }

    public static List<Class> types(Class c, String classpath, Object value) {
        List<Class> types = types(c, classpath);
        return types.stream().filter(type -> {
            Annotation[] annotations = type.getAnnotations();
            return Arrays.stream(annotations).anyMatch(annotation -> call(annotation, "value") == value);
        }).collect(Collectors.toList());
    }

    public static List<Class> types(Class c, String classpath) {
        Reflections reflections = new Reflections(classpath);
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(c);
        return Lists.newArrayList(types);
    }

    public static Object call(Object object, String name) {
        try {
            Method method = object.getClass().getMethod(name);
            return method.invoke(object);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
        }
        return null;
    }

    public static Object call(Object object, String name, Object... parameters) {
        try {
            Method method = method(object.getClass(), name);
            return method.invoke(object, parameters);
        } catch (InvocationTargetException | IllegalAccessException e) {
        }
        return null;
    }

    public static Method method(Class c, String name) {
        Method[] methods = c.getMethods();
        return Arrays.stream(methods).filter(method -> method.getName().equals(name)).findFirst().orElse(null);
    }

    public static <T> T create(Class<T> c, Object... parameters) {
        Constructor<?>[] constructors = c.getConstructors();
        if (constructors.length > 0) {
            Constructor<?> constructor = constructors[0];
            try {
                return (T) constructor.newInstance(parameters);
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        }
        return null;
    }

    public static <T> T enumFromInt(Class<T> c, int value) {
        T[] enums = c.getEnumConstants();
        if (enums.length < value) {
            return null;
        }
        return enums[value - 1];
    }

    public static int enumToInt(Enum object) {
        return object.ordinal() + 1;
    }

    public static Class[] classes(Object... parameters) {
        Class[] classes = new Class[parameters.length];
        int i = 0;
        for (Object parameter : parameters) {
            classes[i] = parameter.getClass();
        }
        return classes;
    }
}
