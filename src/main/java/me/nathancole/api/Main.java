package me.nathancole.api;

import me.nathancole.api.user.registry.UserRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

@SpringBootApplication
public class Main {

    private static UserRegistry m_UserRegistry;

    private static SCryptPasswordEncoder m_PasswordEncoder;

    public static void main(String[] args) {
        m_PasswordEncoder = new SCryptPasswordEncoder();
        SpringApplication.run(Main.class, args);
    }

    public static UserRegistry getUserRegistry() {
        return m_UserRegistry;
    }

    public static SCryptPasswordEncoder getPasswordEncoder() {
        return m_PasswordEncoder;
    }
}
