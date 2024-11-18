package mg.emberframework.util.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import mg.emberframework.manager.exception.ModelValidationException;

public class DateValidator implements ValidatorInterface{

    @Override
    public void validate(String value, Annotation annotation,Field field) throws ModelValidationException {

    }
    
}
