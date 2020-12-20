package pl.stormit.ideas.validation;

import org.springframework.stereotype.Component;
import pl.stormit.ideas.validation.domain.ForbiddenWord;
import pl.stormit.ideas.validation.service.ForbiddenWordService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
class ForbiddenWordsValidator implements Validator {

    private final ForbiddenWordService forbiddenWordService;

    public ForbiddenWordsValidator(ForbiddenWordService forbiddenWordService) {
        this.forbiddenWordService = forbiddenWordService;
    }

    @Override
    public List<String> validate(String input) {
        List<String> errors = forbiddenWordService.getAllForbiddenWords()
                .stream()
                .filter(forbiddenWord -> forbiddenWord.containsForbiddenWord(input))
                .map(ForbiddenWord::getWord)
                .collect(Collectors.toList());

        if (!errors.isEmpty()) {
            return Collections.singletonList("forbidden word");
        }
        return errors;
    }
}