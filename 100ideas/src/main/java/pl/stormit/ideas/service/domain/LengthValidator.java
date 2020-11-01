package pl.stormit.ideas.service.domain;

import java.util.ArrayList;
import java.util.List;

class LengthValidator implements Validator {
    int MIN_LENGTH = 1;
    int MAX_LENGTH = 100;

    @Override
    public List<String> validate(String input) {
        List<String> errors = new ArrayList<>();

        if (input == null || input.length() < MIN_LENGTH) {
            errors.add("Too short");
        }
        else if (input.length() > MAX_LENGTH) {
            errors.add("Too long");
        }
        return errors;
    }
}
