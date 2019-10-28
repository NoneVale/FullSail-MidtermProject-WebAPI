package me.nathancole.api.post.registry;

import me.nathancole.api.datasection.DataSection;
import me.nathancole.api.datasection.Registry;
import me.nathancole.api.post.PostModel;
import me.nathancole.api.user.UserModel;

import java.util.Map;
import java.util.UUID;

public interface PostRegistry extends Registry<PostModel> {

    default PostModel fromDataSection(String key, DataSection section) {
        return new PostModel(key, section);
    }

    default PostModel getPost(UUID uuid) {
        if (uuid == null)
            return null;
        return fromKey(uuid.toString()).orElseGet(null);
    }

    default PostModel getPost(UUID uuid, UserModel userModel) {
        if (uuid == null)
            return null;
        return fromKey(uuid.toString()).orElseGet(() -> register(new PostModel(uuid, userModel)));
    }

    default boolean postExists(UUID uuid) {
        return fromKey(uuid.toString()).isPresent();
    }

    @Deprecated
    Map<String, PostModel> getRegisteredData();
}
