package mg.emberframework.manager.url;

import java.lang.reflect.Method;

import mg.emberframework.annotation.RestApi;
import mg.emberframework.annotation.request.POST;
import mg.emberframework.manager.data.RequestVerb;

public class Mapping {
    Class<?> clazz;
    Method method;
    RequestVerb requestVerb = RequestVerb.GET;

    // Method
    public boolean isRestAPI() {
        return method.isAnnotationPresent(RestApi.class);
    }

    // Construtors
    public Mapping() {}
    public Mapping(Class<?> clazz, Method method) {
        setClazz(clazz);
        setMethod(method);
        setRequestVerb();
    }

    // Getters and setters
    public RequestVerb getRequestVerb() {
        return requestVerb;
    }

    public void setRequestVerb() {
        RequestVerb temp = this.requestVerb;
        if (this.getMethod().isAnnotationPresent(POST.class)) {
            temp = RequestVerb.POST;
        }
        setRequestVerb(temp);
    }

    public void setRequestVerb(RequestVerb requestVerb) {
        this.requestVerb = requestVerb;
    }

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
