package me.nathancole.api;

import com.google.common.collect.Maps;
import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import me.nathancole.api.post.comment.registry.CommentRegistry;
import me.nathancole.api.post.registry.MPostRegistry;
import me.nathancole.api.post.registry.PostRegistry;
import me.nathancole.api.user.UserModel;
import me.nathancole.api.user.registry.MUserRegistry;
import me.nathancole.api.user.registry.UserRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Timer;
import java.util.UUID;

@SpringBootApplication
public class Main {

    private static final char[] m_Chars = "abcdefghijklmnopqrstuvwxyz_1234567890".toCharArray();

    private static HashMap<String, CommentRegistry> m_CommentRegistryMap;
    private static HashMap<String, PostRegistry> m_PostRegistryMap;
    private static HashMap<String, UserRegistry> m_UserRegistryMap;

    private static MongoDatabase m_CommentDatabase;

    private static SCryptPasswordEncoder m_PasswordEncoder;

    private static Timer timer;

    public static void main(String[] args) throws Exception {
        String hostname = "167.114.114.217";
        String username = "hawkeyeapi";
        String password = "hawkeyeAPI$19";

        // GOING TO NEED TO MAKE A DATABASE FOR USERS, POSTS, AND COMMENTS

        ServerAddress address = new ServerAddress(hostname, 27017);

        m_CommentRegistryMap = Maps.newHashMap();
        m_PostRegistryMap = Maps.newHashMap();
        m_UserRegistryMap = Maps.newHashMap();

        MongoCredential credential = MongoCredential.createCredential(username, "hawkeye_users", password.toCharArray());
        MongoDatabase mongoDatabase = new MongoClient(address, credential, new MongoClientOptions.Builder().build()).getDatabase("hawkeye_users");
        for (char chars : m_Chars) {
            UserRegistry userRegistry = new MUserRegistry(String.valueOf(chars), mongoDatabase);
            userRegistry.loadAllFromDb();
            m_UserRegistryMap.put(String.valueOf(chars), userRegistry);
        }
        // UNREG = Unregistered
        UserRegistry unregUserRegistry = new MUserRegistry("UNREG", mongoDatabase);
        unregUserRegistry.loadAllFromDb();
        m_UserRegistryMap.put("UNREG", unregUserRegistry);

        credential = MongoCredential.createCredential(username, "hawkeye_comments", password.toCharArray());
        m_CommentDatabase = new MongoClient(address, credential, new MongoClientOptions.Builder().build()).getDatabase("hawkeye_comments");

        // Create & Load All Post Databases for each user.  Each user has their own post database.
        credential = MongoCredential.createCredential(username, "hawkeye_posts", password.toCharArray());
        mongoDatabase = new MongoClient(address, credential, new MongoClientOptions.Builder().build()).getDatabase("hawkeye_posts");
        for (UserRegistry userRegistry : getUserRegistryMap().values()) {
            for (UserModel userModel : userRegistry.getRegisteredData().values()) {
                PostRegistry postRegistry = new MPostRegistry(userModel.getKey(), mongoDatabase);
                postRegistry.loadAllFromDb();
                m_PostRegistryMap.put(userModel.getKey(), postRegistry);
            }
        }
        PostRegistry unregPostRegistry = new MPostRegistry("UNREG", mongoDatabase);
        unregPostRegistry.loadAllFromDb();
        m_PostRegistryMap.put("UNREG", unregPostRegistry);

        m_PasswordEncoder = new SCryptPasswordEncoder();

        SpringApplication.run(Main.class, args);

        timer = new Timer();
    }

    public static SCryptPasswordEncoder getPasswordEncoder() {
        return m_PasswordEncoder;
    }

    public static UserRegistry getUserRegistry(String p_Username) {
        if (p_Username == null || p_Username.isEmpty() || p_Username.equalsIgnoreCase("UNREG")) {
            return m_UserRegistryMap.get("UNREG");
        }
        
        return m_UserRegistryMap.get(String.valueOf(p_Username.toLowerCase().charAt(0)));
    }

    public static MongoDatabase getCommentDatabase() {
        return m_CommentDatabase;
    }

    public static HashMap<String, CommentRegistry> getCommentRegistryMap() {
        return m_CommentRegistryMap;
    }

    public static HashMap<String, PostRegistry> getPostRegistryMap() {
        return m_PostRegistryMap;
    }

    public static PostRegistry getPostRegistry(String p_Id) {
        if (p_Id == null || p_Id.isEmpty() || p_Id.equalsIgnoreCase("UNREG")) {
            return m_PostRegistryMap.get("UNREG");
        }
        return m_PostRegistryMap.get(p_Id);
    }

    public static CommentRegistry getCommentRegistry(String p_Id) {
        if (p_Id == null || p_Id.isEmpty() || p_Id.equalsIgnoreCase("UNREG")) {
            return m_CommentRegistryMap.get("UNREG");
        }
        return m_CommentRegistryMap.get(p_Id);
    }

    public static HashMap<String, UserRegistry> getUserRegistryMap() {
        return m_UserRegistryMap;
    }

    public static Timer getTimer() {
        return timer;
    }

    public static UserModel getUserById(String id) {
        for (UserRegistry registry : getUserRegistryMap().values()) {
            if (registry.getUser(UUID.fromString(id)) != null) {
                return registry.getUser(UUID.fromString(id));
            }
        }
        return null;
    }
}