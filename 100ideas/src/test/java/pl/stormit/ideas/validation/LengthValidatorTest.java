package pl.stormit.ideas.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class LengthValidatorTest {
    private LengthValidator lengthValidator;

    @BeforeEach
    public void init() {
        lengthValidator = new LengthValidator();
    }

    @ParameterizedTest
    @NullSource
    public void shouldPrintTooSHortIfInputIsNull(String input) {
        // given

        // when
        List<String> list = lengthValidator.validate(input);

        // then
        assertThat(list).containsExactly("Too short");
    }

    @ParameterizedTest
    @ValueSource(strings = {"a"})
    public void shouldPrintTooShort(String input) {
        // given

        // when
        List<String> list = lengthValidator.validate(input);

        // then
        assertThat(list).contains("Too short");
    }

    @ParameterizedTest
    @ValueSource(strings = {"adgjdgjdjgdjgjdjgjdjgdjgjdjgjdjjfdjfdjdjasdjasjdasjdjsdj" +
            "sdksdkskdksdkskdkskdskdkskdskdkskdssjfsjfsfdjfdkfjkdgjkdgjdkgdkgjkdgjkdjgkdjgkdjgkdjgdjkgdgdg"})
    public void shouldPrintTooLong(String input) {
        // given

        // when
        List<String> list = lengthValidator.validate(input);

        // then
        assertThat(list).contains("Too long");
    }
}