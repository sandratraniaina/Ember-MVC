package mg.emberframework.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class CheckController {
    public static List<Class<?>> getClasses(String packageName) throws ClassNotFoundException, IOException {
        List<Class<?>> classes = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');

        Enumeration<URL> resources = classLoader.getResources(path);
        if (resources == null) {
            return classes;
        }

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File file = new File(resource.getFile());
            System.out.println(file.getName());
            if (file.isDirectory()) {
                classes.addAll(getClasses(packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().replace(".class", "");
                classes.add(Class.forName(className));
            }
        }
        return classes;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello World");
        List<Class<?>> list = getClasses("bin.mg.itu.framework.sprint.controller");
        System.out.println(list);
    }

}
