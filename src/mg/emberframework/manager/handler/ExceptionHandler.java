package mg.emberframework.manager.handler;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.http.HttpServletResponse;
import mg.emberframework.manager.exception.UrlNotFoundException;

public class ExceptionHandler {
    private static final Logger logger = Logger.getLogger(ExceptionHandler.class.getName());

    public static void handleException(Exception e, HttpServletResponse response) {
        try {
            if (!response.isCommitted()) {
                if (e instanceof UrlNotFoundException) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                }
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
