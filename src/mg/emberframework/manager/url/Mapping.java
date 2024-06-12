package mg.emberframework.manager.url;

import java.lang.reflect.Method;

public class Mapping {
    String className;
    Method method;

    // Construtors
    public Mapping() {}
    public Mapping(String className, Method methodName) {
        setClassName(className);
        setMethod(methodName);
    }

    // Getters and setters
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
