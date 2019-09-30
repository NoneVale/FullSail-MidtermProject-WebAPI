package me.nathancole.api.post;

import com.google.common.collect.Lists;
import me.nathancole.api.Main;
import me.nathancole.api.user.UserModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
public class PostController {

    @RequestMapping("posts/{id}")
    public static List<PostSanitizer> getPostsForUser(@PathVariable String id) {
        List<PostSanitizer> posts = Lists.newArrayList();

        UserModel userModel = Main.getUserById(id);

        if (userModel != null) {
            userModel.getFollowing().forEach(postUser -> Main.getPostRegistry(postUser.getKey()).getRegisteredData().values().forEach(post -> posts.add(new PostSanitizer(post))));
            Main.getPostRegistry(userModel.getKey()).getRegisteredData().values().forEach(post -> posts.add(new PostSanitizer(post)));
        }

        posts.sort(Comparator.comparing(PostSanitizer::getPostTime).reversed());
        return posts;
    }

    @RequestMapping(method = RequestMethod.POST, value = "posts/register")
    public void postPost(PostRegistrationModel postRegistrationModel) {
        PostFactory.createPost(postRegistrationModel);
    }
}