package pl.stormit.ideas.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class LengthValidatorTest {
    private LengthValidator lengthValidator;

    @BeforeEach
    public void init() {
        lengthValidator = new LengthValidator();
    }

    @Test
    public void shouldPrintTooSHortIfInputIsNull() {
        // when
        List<String> list = lengthValidator.validate(null);

        // then
        assertThat(list).contains("Too short");
    }

    @Test
    public void shouldPrintTooShort() {
        // when
        List<String> list = lengthValidator.validate("a");

        // then
        assertThat(list).contains("Too short");
    }

    @Test
    public void shouldPrintTooLong() {
        // when
        List<String> list = lengthValidator.validate("adgjdgjdjgdjgjdjgjdjgdjgjdjgjdjjfdjfdjdjasdjasjdasjdjsdj" +
                "sdksdkskdksdkskdkskdskdkskdskdkskdssjfsjfsfdjfdkfjkdgjkdgjdkgdkgjkdgjkdjgkdjgkdjgkdjgdjkgdgdg");

        // then
        assertThat(list).contains("Too long");
    }
}