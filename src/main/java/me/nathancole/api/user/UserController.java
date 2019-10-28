package me.nathancole.api.user;

import com.google.common.collect.Lists;
import me.nathancole.api.Main;
import me.nathancole.api.user.registry.UserRegistry;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    @RequestMapping("api/users")
    public List<UserSanitizer> getModels() {
        List<UserSanitizer> sanitizers = Lists.newArrayList();
        for (UserRegistry registry : Main.getUserRegistryMap().values()) {
            for (UserModel model : registry.getRegisteredData().values()) {
                sanitizers.add(new UserSanitizer(model));
            }
        }
        return sanitizers;
    }

    // REGISTRATION METHODS

    @RequestMapping(method = RequestMethod.POST, value ="api/users/register")
    public void registerUser(UserRegistrationModel registrationModel) {
        if (!usernameExists(registrationModel.getUsername()) && !emailExists(registrationModel.getEmail()))
            UserFactory.createUser(registrationModel);
    }

    // LOOKUP METHODS

    @RequestMapping("api/users/username-lookup/{username}")
    public boolean usernameExists(@PathVariable String username) {
        for (UserRegistry registry : Main.getUserRegistryMap().values()) {
            if (registry.usernameExists(username))
                return true;
        }
        return false;
    }

    @RequestMapping("api/users/email-lookup/{email}")
    public boolean emailExists(@PathVariable String email) {
        for (UserRegistry registry : Main.getUserRegistryMap().values()) {
            if (registry.emailExists(email))
                return true;
        }
        return false;
    }


    @RequestMapping("api/users/byid/{id}")
    public UserSanitizer fromId(@PathVariable String id) {
        for (UserRegistry registry : Main.getUserRegistryMap().values()) {
            if (registry.userExists(UUID.fromString(id)))
                return new UserSanitizer(registry.getUser(UUID.fromString(id)));
        }
        return null;
    }

    @RequestMapping("api/users/{section}")
    public List<UserSanitizer> getModels(@PathVariable String section) {
        List<UserSanitizer> sanitizers = Lists.newArrayList();
        for (UserModel model : Main.getUserRegistry(section).getRegisteredData().values()) {
            sanitizers.add(new UserSanitizer(model));
        }
        return sanitizers;
    }

    @RequestMapping("api/users/{section}/{key}")
    public UserSanitizer getSanitizer(@PathVariable String section, @PathVariable String key) {
        UserModel model = Main.getUserRegistry(section).getUser(key);
        return new UserSanitizer(model);
    }

    @RequestMapping("api/users/fromid/{id}")
    public UserSanitizer getFromId(@PathVariable String id) {
        for (UserRegistry registry : Main.getUserRegistryMap().values()) {
            if (registry.getUser(UUID.fromString(id)) != null)
                return new UserSanitizer(registry.getUser(UUID.fromString(id)));
        }
        return null;
    }

    // EMAIL VERIFICATION METHOD

    @RequestMapping("api/users/verify/{id}")
    public String verify(@PathVariable String id) {
        if (UserVerifyEmail.getUser(id) != null) {
            UserVerifyEmail.getUser(id).setVerified(true);
            UserVerifyEmail.removeVerifyLink(id);
            return "Congratulations, your email address has been verified.  You can continue to use our application";
        } else {
            return "I'm sorry, but that verification link either does not exist or has expired.";
        }
    }

    // FOLLOW METHODS

    @RequestMapping (method = RequestMethod.POST, value = "api/users/follow/{following}")
    public void follow(@PathVariable String following, UserIdForm userIdForm) {
        UserModel followingModel = Main.getUserById(following), followerModel = Main.getUserById(userIdForm.getUserId());
        followerModel.follow(followingModel);

    }

    @RequestMapping (method = RequestMethod.POST, value = "api/users/unfollow/{following}")
    public void unfollow(@PathVariable String following, UserIdForm userIdForm) {
        UserModel followingModel = Main.getUserById(following), followerModel = Main.getUserById(userIdForm.getUserId());
        followerModel.unfollow(followingModel);
    }

    // SEARCH METHODS

    @RequestMapping("api/users/search/{keyword}")
    public List<UserSanitizer> getResults(@PathVariable String keyword) {
        if (keyword == null || keyword.isEmpty()) return null;
        List<UserModel> matches = Lists.newArrayList();
        for (UserModel userModel : Main.getUserRegistry(keyword).getRegisteredData().values()) {
            if (userModel.getUsername().startsWith(keyword))
                matches.add(userModel);
        }

        List<UserSanitizer> users = Lists.newArrayList();
        matches.forEach(userModel -> users.add(new UserSanitizer(userModel)));

        users.sort(Comparator.comparing(UserSanitizer::getUsername));
        return users;
    }

    // UPDATE PROFILE PICTURE

    @RequestMapping(method = RequestMethod.POST, value = "api/users/profilePicture")
    public void updateProfilePicture(UserUrlForm urlForm) {
        UserModel userModel = Main.getUserById(urlForm.getUserId());
        userModel.setProfilePictureUrl(urlForm.getUrl());
    }
}