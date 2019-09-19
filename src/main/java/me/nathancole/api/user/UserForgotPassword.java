package me.nathancole.api.user;

import com.google.common.collect.Maps;
import me.nathancole.api.Main;

import java.util.HashMap;
import java.util.Random;
import java.util.TimerTask;

public class UserForgotPassword {

    private static HashMap<Integer, UserModel> emailMap;

    static {
        emailMap = Maps.newHashMap();
    }

    public static void createPasswordCode(UserModel userModel) {
        int code = 100000 + new Random().nextInt(900000);
        emailMap.put(code, userModel);
        Main.getTimer().schedule(new TimerTask() {
            @Override
            public void run() {
                emailMap.remove(code);
            }
        }, 600000);
    }

    public static UserModel getUser(int key) {
        return emailMap.getOrDefault(key, null);
    }

    public static int getPasswordCode(UserModel userModel) {
        for (int i : emailMap.keySet()) {
            if (emailMap.get(i) == userModel) {
                return i;
            }
        }
        return -1;
    }

    public static void removePasswordCode(int key) {
        emailMap.remove(key);
    }
}