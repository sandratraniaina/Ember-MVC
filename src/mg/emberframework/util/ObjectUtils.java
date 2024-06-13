package mg.emberframework.util;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.HashMap;

public class ObjectUtils {
    public static Object getDefaulValue(Class<?>  clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        return getDefaulValue(clazz.getConstructor().newInstance());
    }

    public static Object getDefaulValue(Object object) {
        HashMap<Class<?>, Object> keyValues = new HashMap<Class<?>, Object>();
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
