package me.nathancole.api;

import com.google.common.collect.Maps;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import me.nathancole.api.user.UserFactory;
import me.nathancole.api.user.UserModel;
import me.nathancole.api.user.registry.MUserRegistry;
import me.nathancole.api.user.registry.UserRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;

@SpringBootApplication
public class Main {

    private static final char[] m_Chars = "abcdefghijklmnopqrstuvwxyz_1234567890".toCharArray();
    private static HashMap<String, UserRegistry> m_RegistryMap;

    private static SCryptPasswordEncoder m_PasswordEncoder;

    public static void main(String[] args) {
        String hostname = "167.114.114.217";
        String database = "testdb";
        String username = "admin";
        String password = "password";

        ServerAddress address = new ServerAddress(hostname, 27017);
        MongoCredential credential =
                MongoCredential.createCredential(username, database, password.toCharArray());
        MongoDatabase mongoDatabase = new MongoClient(address, credential, new MongoClientOptions.Builder().build()).getDatabase(database);

        m_RegistryMap = Maps.newHashMap();
        for (char chars : m_Chars) {
            UserRegistry userRegistry = new MUserRegistry(String.valueOf(chars), mongoDatabase);
            userRegistry.loadAllFromDb();
            m_RegistryMap.put(String.valueOf(chars), userRegistry);
        }
        // UNREG = Unregistered
        UserRegistry unregRegistry = new MUserRegistry("UNREG", mongoDatabase);
        unregRegistry.loadAllFromDb();
        m_RegistryMap.put("UNREG", unregRegistry);

        m_PasswordEncoder = new SCryptPasswordEncoder();

        SpringApplication.run(Main.class, args);
    }

    public static SCryptPasswordEncoder getPasswordEncoder() {
        return m_PasswordEncoder;
    }

    public static UserRegistry getUserRegistry(String p_Username) {
        if (p_Username == null || p_Username.isEmpty() || p_Username.equalsIgnoreCase("UNREG")) {
            return m_RegistryMap.get("UNREG");
        }
        
        return m_RegistryMap.get(String.valueOf(p_Username.toLowerCase().charAt(0)));
    }

    public static HashMap<String, UserRegistry> getRegistryMap() {
        return m_RegistryMap;
    }
}