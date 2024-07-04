package mg.emberframework.manager.data;

import jakarta.servlet.http.HttpSession;

public class Session {
    HttpSession userSession;

    // Class methods

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