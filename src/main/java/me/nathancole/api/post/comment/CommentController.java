package me.nathancole.api.post.comment;

import com.google.common.collect.Lists;
import me.nathancole.api.Main;
import me.nathancole.api.post.PostModel;
import me.nathancole.api.user.UserIdForm;
import me.nathancole.api.user.UserModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
public class CommentController {

    @RequestMapping("api/comments/{id}")
    public List<CommentSanitizer> getCommentsForPost(@PathVariable String id) {
        List<CommentSanitizer> comments = Lists.newArrayList();

        PostModel postModel = Main.getPostById(id);;

        if (postModel != null) {
            postModel.getComments().forEach(comment -> comments.add(new CommentSanitizer(comment)));
        }

        comments.sort(Comparator.comparing(CommentSanitizer::getMillisSince).reversed());
        return comments;
    }

    @RequestMapping(method = RequestMethod.POST, value = "api/comments/register")
    public void postComment(CommentRegistrationModel commentRegistrationModel) {
        CommentFactory.createComment(commentRegistrationModel);
    }

    @RequestMapping("api/comments/byid/{id}")
    public CommentSanitizer getPost(@PathVariable String id) {
        return new CommentSanitizer(Main.getCommentById(id));
    }

    @RequestMapping (method = RequestMethod.POST, value = "api/comments/like/{id}")
    public void like(@PathVariable String id, UserIdForm userIdForm) {
        CommentModel commentModel = Main.getCommentById(id);
        UserModel userModel = Main.getUserById(userIdForm.getUserId());
        commentModel.addLike(userModel);
    }

    @RequestMapping (method = RequestMethod.POST, value = "api/comments/unlike/{id}")
    public void unlike(@PathVariable String id, UserIdForm userIdForm) {
        CommentModel commentModel = Main.getCommentById(id);
        UserModel userModel = Main.getUserById(userIdForm.getUserId());
        commentModel.removeLike(userModel);
    }
}
