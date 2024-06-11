package mg.emberframework.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import mg.emberframework.annotation.RequestParameter;

public class ReflectUtils {
    public List<String> getMethodParameterAnnotationValue(Method method) {
        List<String> values = new ArrayList<>();

        for (Class<?> clazz : method.getParameterTypes()) {
            if (clazz.isAnnotationPresent(RequestParameter.class)) {
                RequestParameter annotation = clazz.getAnnotation(RequestParameter.class);
                values.add(annotation.value());
            } else {
                values.add(null);
            }
        }
        
        return values;
    }

    public static Class<?>[] getArgsClasses(Object... args) {
        Class<?>[] classes = new Class[args.length];
        int i = 0;

        for (Object object : args) {
            classes[i] = object.getClass();
            i++;
        }

        return classes;
    }

    public static Object executeMethod(Object object, String methodName, Object... args) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = object.getClass().getMethod(methodName, getArgsClasses(args));
        return method.invoke(object, args);
    }

    public static Object executeClassMethod(Class<?> clazz, String methodName, Object... args)
            throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            InstantiationException {
        Class<?>[] arguments = getArgsClasses(args);
        Object object = clazz.getConstructor().newInstance((Object[]) arguments);
        return executeMethod(object, methodName, args);
    }
}
