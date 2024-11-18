package mg.emberframework.util.validation;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import mg.emberframework.util.validation.validator.FieldValidator;

public class ValidatorRegistry {
    private static final Map<Class<?extends Annotation>, FieldValidator> validators = new HashMap<>();
    
    
}
