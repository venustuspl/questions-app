package pl.stormit.ideas.validation;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class ForbiddenWordsValidator implements Validator {

    @Override
    public List<String> validate(String input) {
        List<String> errors = new ArrayList<>();
        if (input.toLowerCase().contains("brzydkie słowo")) {
            errors.add("forbidden word");
        }
        return errors;
    }
}
