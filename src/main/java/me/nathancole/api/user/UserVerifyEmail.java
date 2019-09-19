package me.nathancole.api.user;

import com.google.common.collect.Maps;
import me.nathancole.api.Main;

import java.util.HashMap;
import java.util.TimerTask;
import java.util.UUID;

public class UserVerifyEmail {

    private static HashMap<String, UserModel> emailMap;

    static {
        emailMap = Maps.newHashMap();
    }

    public static void createVerifyLink(UserModel userModel) {
        String link = UUID.randomUUID().toString();
        emailMap.put(link, userModel);
        Main.getTimer().schedule(new TimerTask() {
            @Override
            public void run() {
                emailMap.remove(link);
            }
        }, 1800000);
    }

    public static UserModel getUser(String key) {
        return emailMap.getOrDefault(key, null);
    }

    public static String getVerificationLink(UserModel userModel) {
        for (String s : emailMap.keySet()) {
            if (emailMap.get(s) == userModel) {
                return "http://167.114.114.217:8080/verify/" + s;
            }
        }
        return null;
    }

    public static void removeVerifyLink(String key) {
        emailMap.remove(key);
    }
}