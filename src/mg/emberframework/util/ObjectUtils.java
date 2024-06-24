package mg.emberframework.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

public class ObjectUtils {
    private static void setObjectAttributesValues(Object instance, String attributeName, String value)
            throws NoSuchFieldException, SecurityException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Field field = instance.getClass().getDeclaredField(attributeName);

        Object fieldValue = castObject(value, field.getType());
        String setterMethodName = ReflectUtils.getSetterMethod(attributeName);
        Method method = instance.getClass().getMethod(setterMethodName, field.getType());
        method.invoke(instance, fieldValue);
    }

    public static Object getParameterInstance(Class<?> classType, String annotationValue, HttpServletRequest request)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException, NoSuchFieldException {
        Object instance = classType.getConstructor().newInstance();

        Enumeration<String> requestParams = request.getParameterNames();

        String attributeName = null, className = null, requestParamName = null, regex = null;

        className = annotationValue.split("\\.")[0];
        regex = className + ".*";

        while (requestParams.hasMoreElements()) {
            requestParamName = requestParams.nextElement();

            if (requestParamName.matches(regex)) {
                attributeName = requestParamName.split("\\.")[1];
                setObjectAttributesValues(instance, attributeName, request.getParameter(requestParamName));
            }
        }

        return instance;
    }

    public static Object castObject(String value, Class<?> clazz) {
        if (value == null) {
            return null;
        } else if (clazz == Integer.TYPE) {
            return Integer.parseInt(value);
        } else if (clazz == Double.TYPE) {
            return Double.parseDouble(value);
        } else if (clazz == Float.TYPE) {
            return Float.parseFloat(value);
        } else {
            return value;
        }
    }

    public static boolean isPrimitive(Class<?> clazz) {
        List<Class<?>> primitiveTypes = new ArrayList<>();
        primitiveTypes.add(Integer.TYPE);
        primitiveTypes.add(Double.TYPE);
        primitiveTypes.add(String.class);

        return primitiveTypes.contains(clazz);
    }

    public static Object getDefaultValue(Class<?> clazz) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        return getDefaultValue(clazz.getConstructor().newInstance());
    }

    public static Object getDefaultValue(Object object) {
        HashMap<Class<?>, Object> keyValues = new HashMap<>();
        keyValues.put(Integer.TYPE, 0);
        keyValues.put(Double.TYPE, 0.0);
        keyValues.put(String.class, "");
        keyValues.put(Date.class, null);

        return keyValues.get(object.getClass());
    }
}
