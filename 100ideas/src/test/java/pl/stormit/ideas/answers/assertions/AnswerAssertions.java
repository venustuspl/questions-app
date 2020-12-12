package pl.stormit.ideas.answers.assertions;

import pl.stormit.ideas.answers.domain.Answer;
import pl.stormit.ideas.answers.domain.dto.AnswerResponse;

public class AnswerAssertions {
    public static AnswerResponseAssert assertThat(AnswerResponse actual) {
        return new AnswerResponseAssert(actual);
    }

    public static AnswerAssert assertThat(Answer actual) {
        return new AnswerAssert(actual);
    }
}
