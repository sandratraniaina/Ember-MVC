package mg.emberframework.manager.url;

public class Mapping {
    String className, methodName;

    // Construtors
    public Mapping() {}
    public Mapping(String className, String methodName) {
        setClassName(className);
        setMethodName(methodName);
    }

    // Getters and setters
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
