package me.nathancole.api.user;

import me.nathancole.api.Main;
import me.nathancole.api.email.EmailType;
import me.nathancole.api.email.Mailer;

import java.util.UUID;

public class UserFactory {

    public static UserModel createUser(String p_Email, String p_FirstName, String p_LastName, String p_Username, String p_Password,
                                       int p_BirthDay, int p_BirthMonth, int p_BirthYear) {
        UserModel userModel = Main.getUserRegistry(p_Username).getUser(UUID.randomUUID());
        userModel.setUsername(p_Username);
        userModel.setEmail(p_Email);
        userModel.setFirstName(p_FirstName);
        userModel.setLastName(p_LastName);
        userModel.setPasswordHash(Main.getPasswordEncoder().encode(p_Password));

        userModel.setBirthDay(p_BirthDay);
        userModel.setBirthMonth(p_BirthMonth);
        userModel.setBirthYear(p_BirthYear);
        System.out.println("User Created");

        try {
            System.out.println("Sending registration email to: " + userModel.getEmail());
            Mailer.sendEmail(EmailType.REGISTRATION, userModel);
        } catch (Exception ignored) {}

        return userModel;
    }

    public static UserModel createUser(UserRegistrationModel model) {
        return createUser(model.getEmail(), model.getFirstName(), model.getLastName(), model.getUsername(), model.getPassword(),
                model.getBirthDay(), model.getBirthMonth(), model.getBirthYear());
    }
}
