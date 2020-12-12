package pl.stormit.ideas.answers.assertions;

import org.assertj.core.api.AbstractAssert;
import pl.stormit.ideas.answers.domain.Answer;
import pl.stormit.ideas.questions.domain.Question;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public class AnswerAssert extends AbstractAssert<AnswerAssert, Answer> {
    public AnswerAssert(Answer actual) {
        super(actual, AnswerAssert.class);
    }

    public static AnswerAssert assertThat(Answer actual) {
        return new AnswerAssert(actual);
    }


    public AnswerAssert hasBody(String body) {
        isNotNull();
        if (!Objects.equals(actual.getBody(), body)) {
            failWithMessage("Expected answer's body to be <%s> but was <%s>", body, actual.getBody());
        }
        return this;
    }

    public AnswerAssert hasQuestion() {
        isNotNull();
        if (actual.getQuestion() == null) {
            failWithMessage("Expected answer's question to be <%s> but was <%s>", Question.class, actual.getQuestion());
        }
        return this;
    }

    public AnswerAssert hasId(UUID id) {
        isNotNull();
        if (!Objects.equals(actual.getId(), id)) {
            failWithMessage("Expected answer's id to be <%s> but was <%s>", id, actual.getId());
        }
        return this;
    }

    public AnswerAssert hasNoId() {
        isNotNull();
        if (actual.getId() != null) {
            failWithMessage("Expected answer's id to be <%s> but was <%s>", null, actual.getId());
        }
        return this;
    }

    public AnswerAssert hasDate(OffsetDateTime date) {
        isNotNull();
        if (!Objects.equals(actual.getCreationDate(), date)) {
            failWithMessage("Expected answer's creation date to be <%s> but was <%s>", date, actual.getCreationDate());
        }
        return this;
    }

    public AnswerAssert hasNoDate() {
        isNotNull();
        if (actual.getCreationDate() != null) {
            failWithMessage("Expected answer's creation date to be <%s> but was <%s>", null, actual.getCreationDate());
        }
        return this;
    }
}
