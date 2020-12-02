package pl.stormit.ideas.validation;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
class ForbiddenWordsValidator implements Validator {

    private List<String> forbiddenWords = List.of("kurwa", "chuj");

    @Override
    public List<String> validate(String input) {
        List<String> errors = new ArrayList<>();
        for (String forbiddenWord : forbiddenWords) {
            if (input.toLowerCase().contains(forbiddenWord)) {
                errors.add("forbidden word: " + forbiddenWord);
            }
        }
        return errors;
    }
}