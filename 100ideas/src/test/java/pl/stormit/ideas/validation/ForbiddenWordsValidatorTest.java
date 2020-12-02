package pl.stormit.ideas.validation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ForbiddenWordsValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"kurwa coś", "bardzo brzydkie kurwa słowa", "chuj", "brzydkie słowa ze słowem chuj"})
    public void shouldReturnForbiddenWord(String input) {
        // given
        ForbiddenWordsValidator validator = new ForbiddenWordsValidator();

        // when
        List<String> list = validator.validate(input);

        // then
        assertThat(list).hasSizeGreaterThan(0);
    }

    @ParameterizedTest
    @ValueSource(strings = {"coś", "bardzo brzydkie słowa", "", "brzydkie słowa z zakazanym słowem"})
    public void shouldReturnEmptyList(String input) {
        // given
        ForbiddenWordsValidator validator = new ForbiddenWordsValidator();

        // when
        List<String> list = validator.validate(input);

        // then
        assertThat(list).hasSize(0);
    }
}