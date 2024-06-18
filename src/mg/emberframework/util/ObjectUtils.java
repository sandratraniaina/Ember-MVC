package mg.emberframework.util;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ObjectUtils {
    public static boolean isPrimitive(Class<?> clazz) {
        List<Class<?>> primitiveTypes = new ArrayList<>();
        primitiveTypes.add(Integer.TYPE);
        primitiveTypes.add(Double.TYPE);
        primitiveTypes.add(String.class);

        return primitiveTypes.contains(clazz);
    }

    public static Object getDefaulValue(Class<?>  clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
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
