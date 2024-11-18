package mg.emberframework.util.validation;

import java.lang.reflect.Field;

import mg.emberframework.manager.exception.ModelValidationException;

public interface ValidatorInterface {
    public void validate(String value, Field field) throws ModelValidationException;
}
