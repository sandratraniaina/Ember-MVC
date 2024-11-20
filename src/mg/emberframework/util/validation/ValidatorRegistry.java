package mg.emberframework.util.validation;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import mg.emberframework.annotation.validation.DateType;
import mg.emberframework.annotation.validation.Email;
import mg.emberframework.annotation.validation.Length;
import mg.emberframework.annotation.validation.Numeric;
import mg.emberframework.annotation.validation.Required;
import mg.emberframework.util.validation.validator.DateValidator;
import mg.emberframework.util.validation.validator.EmailValidator;
import mg.emberframework.util.validation.validator.FieldValidator;
import mg.emberframework.util.validation.validator.LengthValidator;
import mg.emberframework.util.validation.validator.NumericValidator;
import mg.emberframework.util.validation.validator.RequiredValidator;

public class ValidatorRegistry {
    private static final Map<Class<?extends Annotation>, FieldValidator> validators = new HashMap<>();
    
    static {
        validators.put(Length.class, new LengthValidator());
        validators.put(DateType.class, new DateValidator());
        validators.put(Email.class, new EmailValidator());
        validators.put(Required.class, new RequiredValidator());
        validators.put(Numeric.class, new NumericValidator());
    }
    
    public static FieldValidator getValidator(Class<?extends Annotation> annotation) {
        return validators.get(annotation);
    }
}
