package me.nathancole.api.user;

public class UserUrlForm {

    private String userId;
    private String url;

    public UserUrlForm(String userId, String url) {
        this.userId = userId;
        this.url = url;
    }

    public String getUserId() {
        return userId;
    }

    public String getUrl() {
        return url;
    }
}
