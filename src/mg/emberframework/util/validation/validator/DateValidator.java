package mg.emberframework.util.validation.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import mg.emberframework.annotation.validation.DateType;
import mg.emberframework.manager.exception.ModelValidationException;

public class DateValidator implements FieldValidator {

    @Override
    public void validate(String value, Annotation annotation, Field field) throws ModelValidationException {
        DateType dateAnnotation = ((DateType) annotation);
        String format = dateAnnotation.format() != null ? dateAnnotation.format() : "yyyy-MM-dd";

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(value);
        } catch (ParseException e) {
            throw new ModelValidationException("The value '" + value + "' for the field " + field.getName()
                    + " is not a valid date in the format " + format + ".");
        }
    }
}
