package pl.stormit.ideas.validation;

import java.util.List;

class ValidationResult {
    private boolean isValid;
    List<String> error;

    public ValidationResult(boolean isValid, List<String> error) {
        this.isValid = isValid;
        this.error = error;
    }
}