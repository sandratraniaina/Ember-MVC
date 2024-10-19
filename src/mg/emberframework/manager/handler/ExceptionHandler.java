package mg.emberframework.manager.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.http.HttpServletResponse;
import mg.emberframework.manager.exception.UrlNotFoundException;

public class ExceptionHandler {
    private static final Logger logger = Logger.getLogger(ExceptionHandler.class.getName());
    
    private ExceptionHandler() {
    }

    public static void processError(HttpServletResponse response, int statusCode, Exception exception) throws IOException {
        String errorName = getErrorName(statusCode);

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter writer = response.getWriter()) {

        }
    }

    private static String getErrorName(Integer statusCode) {
        if (statusCode == null)
            return "Unknown Error";
        switch (statusCode) {
            case 404:
                return "Not Found";
            case 500:
                return "Internal Server Error";
            default:
                return "HTTP Error " + statusCode;
        }
    }

    public static void handleException(Exception e, HttpServletResponse response) {
        try {
            if (!response.isCommitted()) {
                int errorCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
                if (e instanceof UrlNotFoundException) {
                    errorCode = HttpServletResponse.SC_NOT_FOUND;
                }
                processError(response, errorCode, e);
            }
        } catch (IOException exc) {
            logger.log(Level.SEVERE, exc.getMessage());
        }
    }

    public static void handleExceptions(List<Exception> exceptions, HttpServletResponse response) {
        for (Exception e : exceptions) {
            handleException(e, response);
        }
    }
}
