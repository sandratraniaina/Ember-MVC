package mg.emberframework.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.emberframework.util.ControllerUtils;

public class FrontController extends HttpServlet {
    private ArrayList<Class<?>> controllerClasses;
    private boolean checked = false;

    // Class methods
    private void initVariables() throws ClassNotFoundException, IOException {
        String packageName = this.getInitParameter("package_name");
        ArrayList<Class<?>> classes = ControllerUtils.getControllerClasses(packageName);
        setControllerClasses(classes);
        setChecked(true);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        try {
            out.println("<h1>Welcome to Ember-MVC</h1> <hr>");
            out.println("<p>Looking for a web framework? You are in the right place...</p>");
            out.println(
                    "<p> Ember MVC is a Java-based web framework built on top of the Servlet API. It provides a lightweight alternative to Spring MVC, focusing on core functionalities. </p>");
            out.println("<span>Your URL : <a href = \' \'> " + request.getRequestURI() + "</a></span> <br><br>");
            out.println("Your controllers :");
            out.println("<ul>");

            if (!isChecked()) {
                initVariables();
            }
            
            ArrayList<Class<?>> classes = getControllerClasses();

            for (Class<?> clazz : classes) {
                out.println("<li>" + clazz.getSimpleName() + "</li>");
            }
            out.println("</ul>");
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
    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public ArrayList<Class<?>> getControllerClasses() {
        return controllerClasses;
    }

    public void setControllerClasses(ArrayList<Class<?>> controllerClasses) {
        this.controllerClasses = controllerClasses;
    }
}