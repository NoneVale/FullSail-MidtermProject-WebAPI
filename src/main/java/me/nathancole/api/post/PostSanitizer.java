package me.nathancole.api.post;

import com.google.common.collect.Lists;

import java.util.List;

public class PostSanitizer {

    private String author;
    private String body;

    private List<String> comments;
    private List<String> likes;

    private long postTime;

    public PostSanitizer(PostModel postModel) {
        this.author = postModel.getAuthor().getKey();
        this.body = postModel.getBody();

        comments = Lists.newArrayList();
        postModel.getComments().forEach(comment -> comments.add(comment.getKey()));

        likes = Lists.newArrayList();
        postModel.getLikes().forEach(user -> likes.add(user.getKey()));

        this.postTime = postModel.getPostTime();
    }

    public String getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public List<String> getComments() {
        return comments;
    }

    public List<String> getLikes() {
        return likes;
    }

    public long getPostTime() {
        return postTime;
    }
}
