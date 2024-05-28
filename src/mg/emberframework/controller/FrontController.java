package mg.emberframework.controller;

import java.io.IOException;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import mg.emberframework.manager.MainProcess;
import mg.emberframework.manager.url.Mapping;

public class FrontController extends HttpServlet {
    private HashMap<String, Mapping> URLMappings;

    // Class methods
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MainProcess.handleRequest(request, response);
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
            MainProcess.init(this);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters and setters
    public HashMap<String, Mapping> getURLMapping() {
        return URLMappings;
    }

    public void setURLMapping(HashMap<String, Mapping> urlMapping) {
        this.URLMappings = urlMapping;
    }
}