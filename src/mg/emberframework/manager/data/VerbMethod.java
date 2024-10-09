package mg.emberframework.manager.data;

import java.lang.reflect.Method;

import mg.emberframework.annotation.RestApi;

public class VerbMethod {
    Method method;
    String verb;

    // Method
    public boolean isRestAPI() {
        return method.isAnnotationPresent(RestApi.class);
    }

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
        
        return other.getVerb().equalsIgnoreCase(this.getVerb());
    }
}
