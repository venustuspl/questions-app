package pl.stormit.ideas.validation;

class Message implements ValidationInput{
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String getTextToValidate() {
        return body;
    }
}
