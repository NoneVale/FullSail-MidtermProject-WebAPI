package me.nathancole.api.post.comment.registry;

import me.nathancole.api.datasection.DataSection;
import me.nathancole.api.datasection.Registry;
import me.nathancole.api.post.comment.CommentModel;
import me.nathancole.api.user.UserModel;

import java.util.Map;
import java.util.UUID;

public interface CommentRegistry extends Registry<CommentModel> {

    default CommentModel fromDataSection(String key, DataSection section) {
        return new CommentModel(key, section);
    }

    default CommentModel getComment(UUID uuid, UserModel userModel) {
        if (uuid == null)
            return null;
        return fromKey(uuid.toString()).orElseGet(() -> register(new CommentModel(uuid, userModel)));
    }

    @Deprecated
    Map<String, CommentModel> getRegisteredData();
}
