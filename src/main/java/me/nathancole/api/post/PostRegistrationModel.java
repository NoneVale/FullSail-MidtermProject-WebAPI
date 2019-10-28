package me.nathancole.api.post;

public class PostRegistrationModel {

    private String body;
    private String userId;
    private String photoUrl;

    public PostRegistrationModel(String body, String userId, String photoUrl) {
        this.body = body;
        this.userId = userId;
        this.photoUrl = photoUrl;
    }

    public String getBody() {
        return body;
    }

    public String getUserId() {
        return userId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
