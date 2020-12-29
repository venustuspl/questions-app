package pl.stormit.ideas.answers.assertions;

import org.assertj.core.api.Assertions;
import pl.stormit.ideas.answers.domain.Answer;
import pl.stormit.ideas.answers.domain.dto.AnswerResponse;

public class AnswerAssertions extends Assertions {
    public static AnswerResponseAssert assertThat(AnswerResponse actual) {
        return new AnswerResponseAssert(actual);
    }

    public static AnswerAssert assertThat(Answer actual) {
        return new AnswerAssert(actual);
    }
}
