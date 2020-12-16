package pl.stormit.ideas.answers.assertions;

import org.assertj.core.api.AbstractAssert;
import pl.stormit.ideas.answers.domain.dto.AnswerResponse;

import java.util.Objects;

public class AnswerResponseAssert extends AbstractAssert<AnswerResponseAssert, AnswerResponse> {
    public AnswerResponseAssert(AnswerResponse actual) {
        super(actual, AnswerResponseAssert.class);
    }

    public static AnswerResponseAssert assertThat(AnswerResponse actual) {
        return new AnswerResponseAssert(actual);
    }

    public AnswerResponseAssert hasBody(String body) {
        isNotNull();
        if (!Objects.equals(actual.getBody(), body)) {
            failWithMessage("Expected answerResponse's body to be <%s> but was <%s>", body, actual.getBody());
        }
        return this;
    }

    public AnswerResponseAssert hasId(String id) {
        isNotNull();
        if (!Objects.equals(actual.getId(), id)) {
            failWithMessage("Expected answerResponse's id to be <%s> but was <%s>", id, actual.getId());
        }
        return this;
    }

    public AnswerResponseAssert hasDate(String date) {
        isNotNull();
        if (!Objects.equals(actual.getCreationDate(), date)) {
            failWithMessage("Expected answerResponse's date to be <%s> but was <%s>", date, actual.getCreationDate());
        }
        return this;
    }
}
