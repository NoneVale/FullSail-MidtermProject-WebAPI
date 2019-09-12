package me.nathancole.api;

import me.nathancole.api.user.registry.UserRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    private static UserRegistry m_UserRegistry;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    public static UserRegistry getUserRegistry() {
        return m_UserRegistry;
    }
}
