package mg.emberframework.manager.data;

import java.lang.reflect.Method;

public class VerbMethod {
    Method method;
    String verb;

    // Constructor
    public VerbMethod(Method method, String verb) {
        setMethod(method);
        setVerb(verb);
    }

    // Getters and setters
    public Method getMethod() {
        return method;
    }
    public void setMethod(Method method) {
        this.method = method;
    }
    public String getVerb() {
        return verb;
    }
    public void setVerb(String verb) {
        this.verb = verb;
    }

    // Override methods
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VerbMethod)) {
            return false;
        }
        VerbMethod other = (VerbMethod) obj;
        if (other.getVerb().equalsIgnoreCase(this.getVerb())) {
            return true;
        }
        return false;
    }
}
