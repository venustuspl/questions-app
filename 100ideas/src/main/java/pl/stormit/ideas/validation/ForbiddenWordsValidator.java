package pl.stormit.ideas.validation;

import org.springframework.stereotype.Component;
import pl.stormit.ideas.validation.domain.ForbiddenWord;
import pl.stormit.ideas.validation.service.ForbiddenWordService;

import java.util.ArrayList;
import java.util.List;

@Component
class ForbiddenWordsValidator implements Validator {

    private final ForbiddenWordService forbiddenWordService;

    public ForbiddenWordsValidator(ForbiddenWordService forbiddenWordService) {
        this.forbiddenWordService = forbiddenWordService;
    }

    @Override
    public List<String> validate(String input) {
        List<String> errors = new ArrayList<>();

        List<ForbiddenWord> list = forbiddenWordService.getAllForbiddenWords();
        for (ForbiddenWord forbiddenWord : list) {
            if (input.toLowerCase().contains(forbiddenWord.getWord())) {
                errors.add("forbidden word");
            }
        }
        return errors;
    }
}