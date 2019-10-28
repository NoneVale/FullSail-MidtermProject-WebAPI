package me.nathancole.api.post.comment;

import me.nathancole.api.Main;
import me.nathancole.api.post.PostModel;
import me.nathancole.api.user.UserModel;

import java.util.UUID;

public class CommentFactory {

    public static CommentModel createComment(PostModel post, UserModel author, String body) {
        CommentModel commentModel = Main.getCommentRegistry(post.getKey()).getComment(UUID.randomUUID(), author);
        commentModel.setPostId(post.getKey());
        commentModel.setBody(body);
        commentModel.setPostTime(System.currentTimeMillis());

        post.addComment(commentModel);
        return commentModel;
    }

    public static CommentModel createComment(CommentRegistrationModel commentRegistrationModel) {
        return createComment(Main.getPostById(commentRegistrationModel.getPostId()),
                Main.getUserById(commentRegistrationModel.getUserId()),
                commentRegistrationModel.getBody());
    }
}
