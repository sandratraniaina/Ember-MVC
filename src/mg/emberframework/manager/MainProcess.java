package mg.emberframework.manager;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mg.emberframework.annotation.Controller;
import mg.emberframework.annotation.Get;
import mg.emberframework.controller.FrontController;
import mg.emberframework.url.Mapping;
import mg.emberframework.util.PackageUtils;

public class MainProcess {
    static FrontController frontController;

    public static void init() throws ClassNotFoundException, IOException {
        frontController = new FrontController();

        String packageName = frontController.getInitParameter("package_name");
        ArrayList<Class<?>> classes = (ArrayList<Class<?>>) PackageUtils.getClassesWithAnnotation(packageName,
                Controller.class);

        HashMap<String, Mapping> urlMappings = new HashMap<String, Mapping>();

        for (Class<?> clazz : classes) {
            List<Method> classMethods = PackageUtils.getClassMethodsWithAnnotation(clazz, Get.class);
            String className = clazz.getName();

            for (Method method : classMethods) {
                Get methodAnnotation = method.getAnnotation(Get.class);
                String url = methodAnnotation.value();

                if (url != null && !"".equals(url)) {
                    urlMappings.put(url, new Mapping(className, method.getName()));
                }
            }
        }

        frontController.setURLMapping(urlMappings);
    }
}
