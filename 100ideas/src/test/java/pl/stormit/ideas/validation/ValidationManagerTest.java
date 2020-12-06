package pl.stormit.ideas.validation;

import org.junit.jupiter.api.Test;

import pl.stormit.ideas.questions.domain.Question;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ValidationManagerTest {

    @Test
    public void shouldReturnError() {
        // given
        ValidationManager validationManager = new ValidationManager(List.of(new ValidatorAlwaysErrorMock()));

        // when
        ValidationResult result = validationManager.validate(new Question());

        // then
        assertThat(result.isValid()).isFalse();
    }

    @Test
    public void shouldReturnNoErrors() {
        // given
        ValidationManager validationManager = new ValidationManager(List.of(new EmptyValidatorMock()));

        // when
        ValidationResult result = validationManager.validate(new Question());

        // then
        assertThat(result.isValid()).isTrue();
    }

    @Test
    public void shouldReturnFalseWhenOneOfValidatorsIsFalse() {
        // given
        ValidationManager validationManager = new ValidationManager(List.of(new EmptyValidatorMock(), new ValidatorAlwaysErrorMock()));

        // when
        ValidationResult result = validationManager.validate(new Question());

        // then
        assertThat(result.isValid()).isFalse();
    }

    private static class ValidatorAlwaysErrorMock implements Validator {

        @Override
        public List<String> validate(String input) {
            List<String> errors = new ArrayList<>();

            errors.add("mock");

            return errors;
        }
    }

    private static class EmptyValidatorMock implements Validator {

        @Override
        public List<String> validate(String input) {

            return new ArrayList<>();
        }
    }
}