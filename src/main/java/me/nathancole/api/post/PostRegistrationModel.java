package me.nathancole.api.post;

public class PostRegistrationModel {

    private String body;
    private String userId;

    public PostRegistrationModel(String body, String userId) {
        this.body = body;
        this.userId = userId;
    }

    public String getBody() {
        return body;
    }

    public String getUserId() {
        return userId;
    }
}
