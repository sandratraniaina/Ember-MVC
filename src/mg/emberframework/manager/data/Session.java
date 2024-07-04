package mg.emberframework.manager.data;

import jakarta.servlet.http.HttpSession;

public class Session {
    HttpSession userSession;

    // Class methods
    public void add(String name, Object value) {
        getUserSession().setAttribute(name, value);
    }
    
    public void remove(String name) {
        getUserSession().removeAttribute(name);
    }

    public void clear() {
        getUserSession().invalidate();
    }

    // Contructor
    private Session() {
    }

    // Getters and setters
    public HttpSession getUserSession() {
        return userSession;
    }

    public void setUserSession(HttpSession userSession) {
        this.userSession = userSession;
    }
}