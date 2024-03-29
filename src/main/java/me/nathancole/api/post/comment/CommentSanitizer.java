package me.nathancole.api.post.comment;

import com.google.common.collect.Lists;

import java.util.List;

public class CommentSanitizer {

    private String author;
    private String body;
    private String postId;
    private String commentId;

    private List<String> likes;

    private long postTime;

    public CommentSanitizer(CommentModel commentModel) {
        this.author = commentModel.getAuthor().getKey();
        this.body = commentModel.getBody();
        this.postId = commentModel.getPostId();
        this.commentId = commentModel.getKey();

        this.likes = Lists.newArrayList();
        commentModel.getLikes().forEach(user -> this.likes.add(user.getKey()));

        this.postTime = commentModel.getPostTime();
    }

    public String getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public String getPostId() {
        return postId;
    }

    public String getCommentId() {
        return commentId;
    }

    public List<String> getLikes() {
        return likes;
    }

    public long getPostTime() {
        return postTime;
    }

    public long getMillisSince() {
        return System.currentTimeMillis() - postTime;
    }

}
