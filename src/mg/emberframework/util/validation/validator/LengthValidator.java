package mg.emberframework.util.validation.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import mg.emberframework.annotation.validation.Length;
import mg.emberframework.manager.exception.ModelValidationException;

public class LengthValidator implements FieldValidator {

    @Override
    public void validate(String value, Annotation annotation, Field field) throws ModelValidationException {
        int minLength = ((Length) annotation).length();
        if (((String) value).length() < minLength) {
            throw new ModelValidationException(field.getName() + " must have at least " + minLength + " characters.");
        }
    }

}
