package pl.stormit.ideas.validation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ForbiddenWordsValidatorTest {

    private static ForbiddenWordsValidator validator;

    @BeforeAll
    public static void init() {
        //validator = new ForbiddenWordsValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"kurwa coś", "bardzo brzydkie kurwa słowa", "chuj", "brzydkie słowa ze słowem chuj"})
    public void shouldReturnForbiddenWord(String input) {
        // given
        //List<String> forbiddenWords = validator.getForbiddenWords();

        // when
        List<String> list = validator.validate(input);

        // then
        //assertThat(list).containsAnyElementsOf(forbiddenWords);
    }

    @ParameterizedTest
    @ValueSource(strings = {"coś", "bardzo brzydkie słowa", "", "brzydkie słowa z zakazanym słowem"})
    public void shouldReturnEmptyList(String input) {
        // given

        // when
        List<String> list = validator.validate(input);

        // then
        assertThat(list).isEmpty();
    }
}