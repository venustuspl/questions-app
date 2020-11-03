package pl.stormit.ideas.service.domain;

import java.util.List;

interface Validator {
    List<String> validate(String input);
}
