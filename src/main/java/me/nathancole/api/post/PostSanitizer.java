package me.nathancole.api.post;

import com.google.common.collect.Lists;

import java.util.List;

public class PostSanitizer {

    private String postId;
    private String author;
    private String body;
    private String photoUrl;

    private List<String> comments;
    private List<String> likes;

    private long postTime;

    public PostSanitizer(PostModel postModel) {
        this.postId = postModel.getKey();
        this.author = postModel.getAuthor().getKey();
        this.body = postModel.getBody();
        this.photoUrl = postModel.getPhotoUrl();

        this.comments = Lists.newArrayList();
        postModel.getComments().forEach(comment -> this.comments.add(comment.getKey()));

        this.likes = Lists.newArrayList();
        postModel.getLikes().forEach(user -> this.likes.add(user.getKey()));

        this.postTime = postModel.getPostTime();
    }

    public String getPostId() {
        return this.postId;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getBody() {
        return this.body;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public List<String> getComments() {
        return this.comments;
    }

    public List<String> getLikes() {
        return this.likes;
    }

    public long getMillisSince() {
        return System.currentTimeMillis() - this.postTime;
    }
}