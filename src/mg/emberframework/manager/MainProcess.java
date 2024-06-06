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
import mg.emberframework.manager.exception.DuplicateUrlException;
import mg.emberframework.manager.exception.IllegalReturnTypeException;
import mg.emberframework.manager.exception.InvalidControllerPackageException;
import mg.emberframework.manager.exception.UrlNotFoundException;
import mg.emberframework.manager.url.Mapping;
import mg.emberframework.util.PackageScanner;
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
                ModelView modelView = ((ModelView) result);
                HashMap<String, Object> data = modelView.getData();

                for (String key : data.keySet()) {
                    request.setAttribute(key, data.get(key));
                }

                request.getRequestDispatcher(modelView.getUrl()).forward(request, response);
            } else {
                throw new IllegalReturnTypeException("Invalid return type");
            }

        } catch (UrlNotFoundException | IllegalReturnTypeException e) {
            out.println(e);
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }

    public static void init(FrontController controller) throws ClassNotFoundException, IOException {

        try {
            String packageName = controller.getInitParameter("package_name");

            if (packageName == null) {
                throw new InvalidControllerPackageException("Controller package provider cannot be null");
            }

            HashMap<String, Mapping> urlMappings;
            urlMappings = (HashMap<String, Mapping>) PackageScanner.scanPackage(packageName);

            controller.setURLMapping(urlMappings);
        } catch (InvalidControllerPackageException | DuplicateUrlException e) {
            controller.getExceptions().add(e);
        } catch (Exception e) {
            controller.getExceptions()
                    .add(new Exception("Error during initialization : " + e.getMessage(), e.getCause()));
        }

    }

    // Getters and setters
    public List<Exception> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<Exception> exceptions) {
        this.exceptions = exceptions;
    }
}
