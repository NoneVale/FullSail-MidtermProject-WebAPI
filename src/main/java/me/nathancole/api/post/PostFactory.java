package me.nathancole.api.post;

import me.nathancole.api.Main;
import me.nathancole.api.user.UserModel;

import java.util.UUID;

public class PostFactory {

    public static PostModel createPost(UserModel userModel, String body, String photoUrl) {
        PostModel postModel = Main.getPostRegistry(userModel.getKey()).getPost(UUID.randomUUID(), userModel);
        postModel.setBody(body);
        if (photoUrl != null && !photoUrl.isEmpty())
            postModel.setPhotoUrl(photoUrl);
        postModel.setPostTime(System.currentTimeMillis());

        return postModel;
    }

    public static PostModel createPost(PostRegistrationModel postRegistrationModel) {
        return createPost(Main.getUserById(postRegistrationModel.getUserId()), postRegistrationModel.getBody(), postRegistrationModel.getPhotoUrl());
    }
}
