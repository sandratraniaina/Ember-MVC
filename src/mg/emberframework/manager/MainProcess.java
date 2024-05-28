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
import mg.emberframework.url.Mapping;
import mg.emberframework.util.PackageUtils;

public class MainProcess {
    static FrontController frontController;

    public static void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        try {
            out.println("<h1>Welcome to Ember-MVC</h1> <hr>");

            String url = request.getRequestURI().substring(request.getContextPath().length());
            Mapping mapping = frontController.getURLMapping().get(url);
            out.println("<br>" + url + "<br>");
            if (mapping != null) {
                out.println("<br> Current url (<strong>" + url + "</strong>) matches with the following mapping: <br>");
                out.println("<br> Classname : <strong>" + mapping.getClassName() + "</strong><br> ");
                out.println("<br> Methodname : <strong>" + mapping.getMethodName() + "</strong><br> ");

                Class<?> clazz = Class.forName(mapping.getClassName());
                Object object = clazz.getConstructor().newInstance();
                Method method = clazz.getMethod(mapping.getMethodName());

                String result = method.invoke(object).toString();
                out.println("<p>Result after executing the method: <strong>" + result + "</strong></p>");
            } else {
                out.println("Oops, url not found");
            }

        } catch (Exception e) {
            out.println(e);
        }
    }

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
