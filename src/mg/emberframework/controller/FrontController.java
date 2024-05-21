package mg.emberframework.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import mg.emberframework.util.PackageUtils;
import mg.emberframework.annotation.Controller;
import mg.emberframework.annotation.Get;
import mg.emberframework.url.Mapping;

public class FrontController extends HttpServlet {
    private HashMap<String, Mapping> urlMapping;

    // Class methods
    private void initVariables() throws ClassNotFoundException, IOException {
        String packageName = this.getInitParameter("package_name");
        ArrayList<Class<?>> classes = (ArrayList<Class<?>>)PackageUtils.getClassesWithAnnotation(packageName, Controller.class);

        HashMap<String, Mapping> urlMappings = new HashMap<String, Mapping>();

        for (Class<?> clazz : classes) {
            List<Method> classMethods = PackageUtils.getClassMethodsWithAnnotation(clazz, Get.class);
            String className = clazz.getSimpleName();

            for (Method method : classMethods) {
                Get methodAnnotation = method.getAnnotation(Get.class);
                String url = methodAnnotation.value();

                if (url != null && !"".equals(url)) {
                    urlMappings.put(url, new Mapping(className, method.getName()));
                }
            }
        }
 
        setUrlMapping(urlMappings);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        try {
            out.println("<h1>Welcome to Ember-MVC</h1> <hr>");

        } catch (Exception e) {
            out.println(e);
        }
    }

    // Override methods
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    public void init() throws ServletException {
        try {
            initVariables();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters and setters
    public HashMap<String, Mapping> getUrlMapping() {
        return urlMapping;
    }

    public void setUrlMapping(HashMap<String, Mapping> urlMapping) {
        this.urlMapping = urlMapping;
    }
}