package me.nathancole.api.user.registry;

import me.nathancole.api.datasection.DataSection;
import me.nathancole.api.datasection.Registry;
import me.nathancole.api.user.UserModel;

import java.util.Map;
import java.util.UUID;

public interface UserRegistry extends Registry<UserModel> {

    String NAME = "users";

    default UserModel fromDataSection(String stringKey, DataSection data) {
        return new UserModel(stringKey, data);
    }

    default UserModel getUser(UUID uuid) {
        if (uuid == null)
            return null;
        return fromKey(uuid.toString()).orElseGet(() -> register(new UserModel(uuid)));
    }

    default UserModel usernameLookup(String username) {
        for (UserModel model : getRegisteredData().values())
            if (model.getUsername().equalsIgnoreCase(username))
                return model;
        return null;
    }

    default UserModel getUser(String string) {
        String[] split = string.split(":-:");
        String username = split[0];
        String password = split[1];

        UserModel model = usernameLookup(username);
        // TODO: Add Password Hashing System
        return model;
    }

    @Deprecated
    Map<String, UserModel> getRegisteredData();

    default boolean userExists(UUID uuid) {
        return fromKey(uuid.toString()).isPresent();
    }

    default boolean usernameExists(String username) {
        for (UserModel model : getRegisteredData().values())
            if (model.getUsername().equalsIgnoreCase(username))
                return true;
        return false;
    }
}