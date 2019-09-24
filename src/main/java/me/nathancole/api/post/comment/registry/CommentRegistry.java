package me.nathancole.api.post.comment.registry;

import me.nathancole.api.datasection.Registry;
import me.nathancole.api.post.comment.CommentModel;

import java.util.UUID;

public interface CommentRegistry extends Registry<CommentModel> {

    default CommentModel getComment(UUID uuid) {
        if (uuid == null)
            return null;
        return fromKey(uuid.toString()).orElseGet(() -> register(new CommentModel(uuid)));
    }
}
