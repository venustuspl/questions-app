package pl.stormit.ideas.validation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ForbiddenWordsValidatorTest {

    @Test
    public void shouldReturnForbiddenWord() {
        // given
        ForbiddenWordsValidator validator = new ForbiddenWordsValidator();

        // when
        List<String> list = validator.validate("brzydkie słowo");

        // then
        assertThat(list).contains("forbidden word");
    }
}