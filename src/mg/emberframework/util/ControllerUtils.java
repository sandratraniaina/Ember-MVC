package mg.emberframework.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import mg.emberframework.annotation.Controller;

public class ControllerUtils {
    public static ArrayList<Class<?>> getClasses(String packageName) throws ClassNotFoundException, IOException {
        ArrayList<Class<?>> classes = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');

        URL resource = classLoader.getResource(path);

        if (resource == null) {
            return classes;
        }

        File packageDir = new File(resource.getFile().replace("%20", " "));
        
        for (File file : packageDir.listFiles()) {
            if (file.isDirectory()) {
                classes.addAll(ControllerUtils.getClasses(packageName + "." + file.getName()));
            } else {
                String className = packageName + "." + FileUtils.getSimpleFileName(file.getName(), "class");
                classes.add(Class.forName(className));
            }
        }
        
        return classes;
    }

    public static ArrayList<Class<?>> getControllerClasses(String packageName) throws ClassNotFoundException, IOException {
        ArrayList<Class<?>> classes = getClasses(packageName);

        ArrayList<Class<?>> result = new ArrayList<Class<?>>();
        
        for(Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                result.add(clazz);
            }
        }

        return result;
    }

}
