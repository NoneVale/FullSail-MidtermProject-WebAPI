package me.nathancole.api.user;

import com.google.common.collect.Lists;
import me.nathancole.api.Main;
import me.nathancole.api.user.registry.UserRegistry;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {

    public static List<UserRegistrationModel> models;

    static {
        models = Lists.newArrayList();
    }

    @RequestMapping
    public List<UserSanitizer> getModels() {
        List<UserSanitizer> sanitizers = Lists.newArrayList();
        for (UserRegistry registry : Main.getRegistryMap().values()) {
            for (UserModel model : registry.getRegisteredData().values()) {
                sanitizers.add(new UserSanitizer(model));
            }
        }
        return sanitizers;
    }

    @RequestMapping("users/{section}")
    public List<UserSanitizer> getModels(@PathVariable String section) {
        List<UserSanitizer> sanitizers = Lists.newArrayList();
        for (UserModel model : Main.getUserRegistry(section).getRegisteredData().values()) {
            sanitizers.add(new UserSanitizer(model));
        }
        return sanitizers;
    }

    @RequestMapping("users/{section}/{id}")
    public UserSanitizer getSanitizer(@PathVariable String section, @PathVariable String id) {
        UserModel model = Main.getUserRegistry(section).getUser(id);
        return new UserSanitizer(model);
    }

    @RequestMapping(method = RequestMethod.POST, value ="registrations")
    public void registerUser(@RequestBody UserRegistrationModel registrationModel) {
        // Needed to add @RequestBody to this method (May have to remove later idk)
        if (!usernameExists(registrationModel.getUsername()) && !emailExists(registrationModel.getEmail()))
            UserFactory.createUser(registrationModel);
    }

    @RequestMapping("registration")
    public List<UserRegistrationModel> getRegistrationModels() {
        return models;
    }

    @RequestMapping("username-lookup/{username}")
    public boolean usernameExists(@PathVariable String username) {
        for (UserRegistry registry : Main.getRegistryMap().values()) {
            if (registry.usernameExists(username))
                return true;
        }
        return false;
    }

    @RequestMapping("email-lookup/{email}")
    public boolean emailExists(@PathVariable String email) {
        for (UserRegistry registry : Main.getRegistryMap().values()) {
            if (registry.emailExists(email))
                return true;
        }
        return false;
    }
}