package pl.stormit.ideas.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.stormit.ideas.validation.domain.ForbiddenWord;
import pl.stormit.ideas.validation.service.ForbiddenWordService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ForbiddenWordsValidatorTest {
    private ForbiddenWordsValidator forbiddenWordsValidator;
    private ForbiddenWordService forbiddenWordService = mock(ForbiddenWordService.class);

    @BeforeEach
    public void init() {
        forbiddenWordsValidator = new ForbiddenWordsValidator(forbiddenWordService);
    }

    @ParameterizedTest
    @ValueSource(strings = {"kurwa coś", "bardzo brzydkie kurwa słowa", "chuj", "brzydkie słowa ze słowem chuj"})
    public void shouldContainForbiddenWord(String input) {
        // given
        when(forbiddenWordService.getAllForbiddenWords()).thenReturn(provideForbiddenWords());

        // when
        List<String> list = forbiddenWordsValidator.validate(input);

        // then
        assertThat(list).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"coś", "bardzo brzydkie słowa", "", "brzydkie słowa z zakazanym słowem"})
    public void shouldReturnEmptyList(String input) {
        // given
        when(forbiddenWordService.getAllForbiddenWords()).thenReturn(provideForbiddenWords());

        // when
        List<String> list = forbiddenWordsValidator.validate(input);

        // then
        assertThat(list).isEmpty();
    }

    private static List<ForbiddenWord> provideForbiddenWords() {
        return Arrays.asList(new ForbiddenWord("kurwa"), new ForbiddenWord("chuj"));
    }
}