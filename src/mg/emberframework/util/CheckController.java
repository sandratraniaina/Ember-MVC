package mg.emberframework.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class CheckController {
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
                classes.addAll(CheckController.getClasses(packageName + "." + file.getName()));
            } else {
                String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                classes.add(Class.forName(className));
            }
        }
        
        return classes;
    }

}
