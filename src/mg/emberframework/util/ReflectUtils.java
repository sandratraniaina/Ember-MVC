package mg.emberframework.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import mg.emberframework.annotation.RequestParameter;
import mg.emberframework.manager.exception.AnnotationNotPresentException;
import mg.emberframework.manager.url.Mapping;

public class ReflectUtils {

    public static String getMethodName(String initial, String attributeName) {
        return initial + Character.toUpperCase(attributeName.charAt(0)) + attributeName.substring(1);
    }

    public static String getSetterMethod(String attributeName) {
        return getMethodName("set", attributeName);
    }

    public static Object executeRequestMethod(Mapping mapping, HttpServletRequest request)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, InstantiationException, ClassNotFoundException, NoSuchFieldException, AnnotationNotPresentException {
        List<Object> objects = new ArrayList<>();

        Class<?> objClass = Class.forName(mapping.getClassName());
        Method method = mapping.getMethod();

        for (Parameter parameter : method.getParameters()) {
            Class<?> clazz = parameter.getType();
            Object object = ObjectUtils.getDefaultValue(clazz);
            if (!parameter.isAnnotationPresent(RequestParameter.class)) {
                throw new AnnotationNotPresentException("ETU2468 , one of you parameter does not have `RequestParameter` annotation");
            }

            object = ObjectUtils.getParameterInstance(request, parameter, clazz, object);

            objects.add(object);
        }

        return executeClassMethod(objClass, method.getName(), objects.toArray());
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
        // Class<?>[] arguments = getArgsClasses(args);
        Object object = clazz.getConstructor().newInstance();
        return executeMethod(object, methodName, args);
    }
}
