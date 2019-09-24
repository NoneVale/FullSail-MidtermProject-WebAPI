package me.nathancole.api.post;

import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    @RequestMapping("posts/{id}")
    public List<PostModel> getPostsForUser(@PathVariable String id) {

        return Lists.newArrayList();
    }
}
