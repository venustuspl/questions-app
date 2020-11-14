package pl.stormit.ideas.validation;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValidationManager {
    static List<Validator> validators;

    public ValidationManager() {
        validators.add(new LengthValidator());
        validators.add(new ForbiddenWordsValidator());
    }

    public ValidationManager(List<Validator> validators) {
        this.validators.addAll(validators);
    }

    public ValidationResult validate(ValidationInput input) {
        List<String> errors = new ArrayList<>();

        validators.forEach( validator -> {
            errors.addAll(validator.validate(input.getTextToValidate()));
        });

       return new ValidationResult(errors.isEmpty(), errors);
    }
}
