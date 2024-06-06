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
import mg.emberframework.manager.data.ModelView;
import mg.emberframework.manager.exception.IllegalReturnTypeException;
import mg.emberframework.manager.exception.UrlNotFoundException;
import mg.emberframework.manager.url.Mapping;
import mg.emberframework.util.PackageUtils;
import mg.emberframework.util.ReflectUtils;

public class MainProcess {
    static FrontController frontController;
    private List<Exception> exceptions;

    public static void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter out = response.getWriter();

        try {

            String url = request.getRequestURI().substring(request.getContextPath().length());
            Mapping mapping = frontController.getURLMapping().get(url);

            if (mapping == null) {
                throw new UrlNotFoundException("Oops, url not found!");
            }

            Class<?> clazz = Class.forName(mapping.getClassName());
            Object result = ReflectUtils.executeClassMethod(clazz, mapping.getMethodName());
            
            if (result instanceof String) {
                out.println(result.toString());
            } else if (result instanceof ModelView) {
                ModelView modelView = ((ModelView)result);
                HashMap<String, Object> data = modelView.getData();

                for (String key : data.keySet()) {
                    request.setAttribute(key, data.get(key));
                }

                request.getRequestDispatcher(modelView.getUrl()).forward(request, response);
            } else {
                throw new IllegalReturnTypeException("Invalid return type");
            }

        } catch (UrlNotFoundException | IllegalReturnTypeException e ) {
            out.println(e);
        } catch (Exception e) {
            e.printStackTrace(out);
        }
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

    // Getters and setters
    public List<Exception> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<Exception> exceptions) {
        this.exceptions = exceptions;
    }
}
