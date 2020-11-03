package pl.stormit.ideas.service.domain;

import java.util.ArrayList;
import java.util.List;

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
