package pl.stormit.ideas.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ValidationManager {
    private List<Validator> validators;

    @Autowired
    public ValidationManager(List<Validator> validators) {
        this.validators = validators;
    }

    public ValidationResult validate(ValidationInput input) {
        List<String> errors = new ArrayList<>();

        validators.forEach( validator -> {
            errors.addAll(validator.validate(input.getTextToValidate()));
        });

       return new ValidationResult(errors.isEmpty(), errors);
    }
}
