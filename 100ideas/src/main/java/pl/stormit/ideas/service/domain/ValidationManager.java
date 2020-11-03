package pl.stormit.ideas.service.domain;

import java.util.ArrayList;
import java.util.List;

class ValidationManager {
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

        ValidationResult result = new ValidationResult(errors.isEmpty(), errors);

        return result;
    }
}
