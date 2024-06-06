package mg.emberframework.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import mg.emberframework.manager.MainProcess;
import mg.emberframework.manager.exception.DuplicateUrlException;
import mg.emberframework.manager.exception.IllegalReturnTypeException;
import mg.emberframework.manager.exception.InvalidControllerPackageException;
import mg.emberframework.manager.exception.UrlNotFoundException;
import mg.emberframework.manager.handler.ExceptionHandler;
import mg.emberframework.manager.url.Mapping;

public class FrontController extends HttpServlet {
    private HashMap<String, Mapping> URLMappings;
    private List<Exception> exceptions;

    // Class methods
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            MainProcess.handleRequest(this, request, response);
        } catch (UrlNotFoundException |  IllegalReturnTypeException e) {
            ExceptionHandler.handleException(e, response);
        } catch (Exception e) {
            ExceptionHandler.handleException(new Exception("An error has occured while processing your request : " + e.getMessage()), response);
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
            MainProcess.init(this);
        } catch (InvalidControllerPackageException | DuplicateUrlException e) {
            getExceptions().add(e);
        } catch (Exception e) {
            getExceptions().add(new Exception(e.getMessage(), e.getCause()));
        }
    }

    // Getters and setters
    public HashMap<String, Mapping> getURLMapping() {
        return URLMappings;
    }

    public void setURLMapping(HashMap<String, Mapping> urlMapping) {
        this.URLMappings = urlMapping;
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<Exception> exceptions) {
        this.exceptions = exceptions;
    }
}