package pl.stormit.ideas.service.domain;

import java.util.List;

class ValidationResult {
    private boolean isValid;
    List<String> error;

    public ValidationResult(boolean isValid, List<String> error) {
        this.isValid = isValid;
        this.error = error;
    }
}