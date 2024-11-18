package mg.emberframework.util.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import mg.emberframework.manager.exception.ModelValidationException;

public class NumericValidator implements ValidatorInterface{

    @Override
    public void validate(String value, Annotation annotation, Field field) throws ModelValidationException {
        if (!(value != null && value.matches("\\d+"))) {
            throw new ModelValidationException(field.getName() + " should be numeric");
        }
    }
    
}
