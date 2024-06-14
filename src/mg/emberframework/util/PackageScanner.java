package mg.emberframework.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mg.emberframework.annotation.Controller;
import mg.emberframework.annotation.Get;
import mg.emberframework.manager.exception.DuplicateUrlException;
import mg.emberframework.manager.exception.InvalidControllerPackageException;
import mg.emberframework.manager.url.Mapping;

public class PackageScanner {
    public static Map<String, Mapping> scanPackage(String packageName)
            throws ClassNotFoundException, IOException, DuplicateUrlException,InvalidControllerPackageException {
        if (packageName == null || packageName.isBlank()) {
            throw new InvalidControllerPackageException("Controller package provider cannot be null");
        }

        Map<String, Mapping> result = new HashMap<>();

        ArrayList<Class<?>> classes = (ArrayList<Class<?>>) PackageUtils.getClassesWithAnnotation(packageName,
                Controller.class);
        for (Class<?> clazz : classes) {
            List<Method> classMethods = PackageUtils.getClassMethodsWithAnnotation(clazz, Get.class);
            String className = clazz.getName();

            for (Method method : classMethods) {
                Get methodAnnotation = method.getAnnotation(Get.class);
                String url = methodAnnotation.value();

                if (result.containsKey(url)) {
                    throw new DuplicateUrlException("Duplicated url \"" + url + "\"");
                }

                if (url != null && !"".equals(url)) {
                    result.put(url, new Mapping(className, method));
                }
            }
        }

        return result;
    }
}
