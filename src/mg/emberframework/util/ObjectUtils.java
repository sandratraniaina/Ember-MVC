package mg.emberframework.util;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

public class ObjectUtils {
    public static List<String> getObjectAnnotationValues(String className, HttpServletRequest request) {
        List<String> values = new ArrayList<>();

        return values;
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
        return getDefaulValue(clazz.getConstructor().newInstance());
    }

    public static Object getDefaulValue(Object object) {
        HashMap<Class<?>, Object> keyValues = new HashMap<>();
        keyValues.put(Integer.TYPE, 0);
        keyValues.put(Double.TYPE, 0.0);
        keyValues.put(String.class, "");
        keyValues.put(Date.class, null);

        Object defaultValue = keyValues.get(object.getClass());
        if (defaultValue == null) {
            return object == null;
        }
        return defaultValue.equals(object);
    }
}
