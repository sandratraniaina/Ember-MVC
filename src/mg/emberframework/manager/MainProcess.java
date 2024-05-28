package mg.emberframework.manager;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.emberframework.annotation.Controller;
import mg.emberframework.annotation.Get;
import mg.emberframework.controller.FrontController;
import mg.emberframework.manager.url.Mapping;
import mg.emberframework.util.PackageUtils;
import mg.emberframework.util.ReflectUtils;
import mg.emberframework.util.TagBuilder;

public class MainProcess {
    static FrontController frontController;

    public static void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter out = response.getWriter();

        String title = TagBuilder.heading("Welcome to Ember-MVC", 1);
        title += TagBuilder.unclosedTag("hr");
        
        String message = "";

        try {

            String url = request.getRequestURI().substring(request.getContextPath().length());
            Mapping mapping = frontController.getURLMapping().get(url);

            message += TagBuilder.enclose("URL: " +  TagBuilder.bold(url), "br");
            
            if (mapping != null) {
                message += TagBuilder.paragraph("Current url matches with the following mapping : ");

                Class<?> clazz = Class.forName(mapping.getClassName());
                String result = ReflectUtils.executeClassMethod(clazz, mapping.getMethodName()).toString();

                String info = "";

                info += TagBuilder.enclose("Classname : " + TagBuilder.bold(mapping.getClassName()), "li");
                info += TagBuilder.enclose("MethodName : " + TagBuilder.bold(mapping.getMethodName()), "li");
                info += TagBuilder.enclose("Method execution result: " + TagBuilder.bold(result), "li");

                message += TagBuilder.enclose(info, "ul");
            } else {
                message = TagBuilder.bold("Oops, url not found");
            }

        } catch (Exception e) {
            message += TagBuilder.bold(e.getMessage());
        }

        out.println(title);
        out.println(message);
    }

    public static void init(FrontController controller) throws ClassNotFoundException, IOException {
        frontController = controller;

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
