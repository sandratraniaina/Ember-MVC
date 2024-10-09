package mg.emberframework.manager.url;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import mg.emberframework.manager.data.VerbMethod;
import mg.emberframework.manager.exception.DuplicateUrlException;

public class Mapping {
    Class<?> clazz;
    Set<VerbMethod> verbMethods = new HashSet<>();

    // Method
    public void addVerbMethod(VerbMethod verbMethod) throws DuplicateUrlException {
        if (getVerbMethods().contains(verbMethod)) {
            throw new DuplicateUrlException("Duplicate url method!!");
        }
        getVerbMethods().add(verbMethod);
    }

    // Construtors
    public Mapping() {}
    public Mapping(Class<?> clazz) {
        setClazz(clazz);
    }

    // Getters and setters
    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Set<VerbMethod> getVerbMethods() {
        return verbMethods;
    }

    public void setVerbMethods(Set<VerbMethod> verbMethods) {
        this.verbMethods = verbMethods;
    }
}
