package me.nathancole.api.post.comment;

import me.nathancole.api.Main;
import me.nathancole.api.post.PostModel;

import java.util.UUID;

public class CommentFactory {

    public static CommentModel createComment(PostModel post, String body) {
        CommentModel commentModel = Main.getCommentRegistry(post.getKey()).getComment(UUID.randomUUID(), post.getAuthor());
        commentModel.setPostId(post.getKey());
        commentModel.setBody(body);
        commentModel.setPostTime(System.currentTimeMillis());

        return commentModel;
    }
}
