package pl.stormit.ideas.validation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ForbiddenWordsValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {"brzydkie słowo"})
    public void shouldReturnForbiddenWord(String input) {
        // given
        ForbiddenWordsValidator validator = new ForbiddenWordsValidator();

        // when
        List<String> list = validator.validate(input);

        // then
        assertThat(list).contains("forbidden word");
    }
}