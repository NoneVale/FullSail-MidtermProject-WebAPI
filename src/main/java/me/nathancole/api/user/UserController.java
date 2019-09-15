package me.nathancole.api.user;

import com.google.common.collect.Lists;
import me.nathancole.api.Main;
import me.nathancole.api.user.registry.UserRegistry;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

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

    @RequestMapping(method = RequestMethod.POST, value ="users")
    public void registerUser(UserRegistrationModel registrationModel) {
        UserFactory.createUser(registrationModel);
    }
}
