package me.nathancole.api.post.comment;

public class CommentRegistrationModel {

    private String body;
    private String userId;
    private String postId;

    public CommentRegistrationModel(String body, String userId, String postId) {
        this.body = body;
        this.userId = userId;
        this.postId = postId;
    }

    public String getBody() {
        return body;
    }

    public String getUserId() {
        return userId;
    }

    public String getPostId() {
        return postId;
    }
}
