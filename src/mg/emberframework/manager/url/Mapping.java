package mg.emberframework.manager.url;

import java.lang.reflect.Method;

import mg.emberframework.annotation.RestApi;

public class Mapping {
    Class<?> clazz;
    Method method;

    // Method
    public boolean isRestAPI() {
        return method.isAnnotationPresent(RestApi.class);
    }

    // Construtors
    public Mapping() {}
    public Mapping(Class<?> clazz, Method methodName) {
        setClazz(clazz);
        setMethod(methodName);
    }

    // Getters and setters
    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
