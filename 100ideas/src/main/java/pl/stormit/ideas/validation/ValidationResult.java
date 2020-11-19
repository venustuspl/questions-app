package pl.stormit.ideas.validation;

import java.util.List;

public class ValidationResult {
    private boolean isValid;
    private List<String> error;

    public ValidationResult(boolean isValid, List<String> error) {
        this.isValid = isValid;
        this.error = error;
    }

    public boolean isValid() {
        return isValid;
    }

    public List<String> getError() {
        return error;
    }

}