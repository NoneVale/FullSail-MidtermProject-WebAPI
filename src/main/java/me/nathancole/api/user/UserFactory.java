package me.nathancole.api.user;

import me.nathancole.api.Main;

import java.util.UUID;

public class UserFactory {

    public static UserModel createUser(String username, String password) {
        UserModel userModel = Main.getUserRegistry(username).getUser(UUID.randomUUID());
        //TODO: Finish Factory method to create users
        //userModel.setUsername(username);
        userModel.setPasswordHash(password);
        userModel.setBirthDay(22);
        userModel.setBirthYear(1999);
        userModel.setBirthMonth(3);
        return userModel;
    }
}
