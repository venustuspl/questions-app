package pl.stormit.ideas.validation;

import java.util.List;

interface Validator {
    List<String> validate(String input);
}
