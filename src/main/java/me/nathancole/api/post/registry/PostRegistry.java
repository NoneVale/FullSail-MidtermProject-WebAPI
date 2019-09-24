package me.nathancole.api.post.registry;

import me.nathancole.api.datasection.DataSection;
import me.nathancole.api.datasection.Registry;
import me.nathancole.api.post.PostModel;

import java.util.UUID;

public interface PostRegistry extends Registry<PostModel> {

    default PostModel fromDataSection(String key, DataSection section) {
        return new PostModel(key, section);
    }

    default PostModel getPost(UUID uuid) {
        if (uuid == null)
            return null;
        return fromKey(uuid.toString()).orElseGet(() -> register(new PostModel(uuid)));
    }
}
