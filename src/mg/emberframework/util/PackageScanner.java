package mg.emberframework.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mg.emberframework.annotation.Controller;
import mg.emberframework.annotation.request.Url;
import mg.emberframework.manager.data.RequestVerb;
import mg.emberframework.manager.data.VerbMethod;
import mg.emberframework.manager.exception.DuplicateUrlException;
import mg.emberframework.manager.exception.InvalidControllerPackageException;
import mg.emberframework.manager.url.Mapping;

public class PackageScanner {
    private PackageScanner() {
    }

    public static Map<String, Mapping> scanPackage(String packageName)
            throws ClassNotFoundException, IOException, DuplicateUrlException, InvalidControllerPackageException {
        if (packageName == null || packageName.isBlank()) {
            throw new InvalidControllerPackageException("Controller package provider cannot be null");
        }

        Map<String, Mapping> result = new HashMap<>();

        ArrayList<Class<?>> classes = (ArrayList<Class<?>>) PackageUtils.getClassesWithAnnotation(packageName,
                Controller.class);
        for (Class<?> clazz : classes) {
            List<Method> classMethods = PackageUtils.getClassMethodsWithAnnotation(clazz, Url.class);

            for (Method method : classMethods) {
                Url methodAnnotation = method.getAnnotation(Url.class);
                String url = methodAnnotation.value();
                
                VerbMethod verbMethod = new VerbMethod(method, RequestVerb.getMethodVerb(method));

                Mapping mapping = result.get(url);
                
                if (mapping != null) {
                    mapping.addVerbMethod(verbMethod);
                } else {
                    mapping = new Mapping(clazz);

                    mapping.addVerbMethod(verbMethod);
                    result.put(url, mapping);
                }
            }
        }

        return result;
    }
}
