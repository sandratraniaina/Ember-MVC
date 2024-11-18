package mg.emberframework.util.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import mg.emberframework.manager.exception.ModelValidationException;

public class RequiredValidator implements ValidatorInterface{

    @Override
    public void validate(String value, Annotation annotation, Field field) throws ModelValidationException {
        if (value == null || "".equals(value)) {
            throw new ModelValidationException(field.getName() + " should is Required");
        }
    }
    
}
