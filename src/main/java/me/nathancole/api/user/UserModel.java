package me.nathancole.api.user;

import com.google.common.collect.Maps;
import me.nathancole.api.Main;
import me.nathancole.api.datasection.DataSection;
import me.nathancole.api.datasection.Model;

import java.util.Map;
import java.util.UUID;

public class UserModel implements Model {

    private String m_Email;
    private String m_FirstName;
    private String m_Key;
    private String m_LastName;
    private String m_PasswordHash;
    private String m_ProfilePictureUrl;
    private String m_Username;

    private int m_BirthDay;
    private int m_BirthMonth;
    private int m_BirthYear;
    private int m_ForgotPasswordCode;

    private boolean m_Verified;

    private UUID m_Uuid;

    public UserModel(UUID p_Uuid) {
        this.m_Email = "";
        this.m_FirstName = "";
        this.m_Key = p_Uuid.toString();
        this.m_LastName = "";
        this.m_PasswordHash = "";
        this.m_ProfilePictureUrl = "";
        this.m_Username = "";

        this.m_BirthDay = 0;
        this.m_BirthMonth = 0;
        this.m_BirthYear = 0;
        this.m_ForgotPasswordCode = -1;

        this.m_Verified = false;

        this.m_Uuid = p_Uuid;
    }

    public UserModel(String p_Key, DataSection p_Data) {
        this.m_Email = p_Data.getString("email");
        this.m_FirstName = p_Data.getString("first-name");
        this.m_Key = p_Key;
        this.m_LastName = p_Data.getString("last-name");
        this.m_PasswordHash = p_Data.getString("password-hash");
        if (p_Data.isSet("profile-picture-url"))
            this.m_ProfilePictureUrl = p_Data.getString("profile-picture-url");
        else
            this.m_ProfilePictureUrl = "";
        this.m_Username = p_Data.getString("username");
        
        this.m_BirthDay = p_Data.getInt("birth-day");
        this.m_BirthMonth = p_Data.getInt("birth-month");
        this.m_BirthYear = p_Data.getInt("birth-year");
        if (p_Data.isSet("forgot-password-code"))
            this.m_ForgotPasswordCode = p_Data.getInt("forgot-password-code");
        else
            this.m_ForgotPasswordCode = -1;

        this.m_Verified = p_Data.getBoolean("verified");

        this.m_Uuid = UUID.fromString(p_Key);
    }

    public String getEmail() {
        return m_Email;
    }

    public void setEmail(String p_Email) {
        this.m_Email = p_Email;
        Main.getUserRegistry(getUsername()).register(this);
    }

    public String getFirstName() {
        return m_FirstName;
    }

    public void setFirstName(String p_FirstName) {
        this.m_FirstName = p_FirstName;
        Main.getUserRegistry(getUsername()).register(this);
    }

    public String getLastName() {
        return m_LastName;
    }

    public void setLastName(String p_LastName) {
        this.m_LastName = p_LastName;
        Main.getUserRegistry(getUsername()).register(this);
    }

    public String getPasswordHash() {
        return m_PasswordHash;
    }

    public void setPasswordHash(String p_PasswordHash) {
        this.m_PasswordHash = p_PasswordHash;
        Main.getUserRegistry(getUsername()).register(this);
    }

    public String getUsername() {
        return m_Username;
    }

    public void setUsername(String p_Username) {
        Main.getUserRegistry(getUsername()).remove(this);
        this.m_Username = p_Username;
        Main.getUserRegistry(getUsername()).register(this);
    }

    public int getBirthDay() {
        return m_BirthDay;
    }

    public void setBirthDay(int p_BirthDay) {
        this.m_BirthDay = p_BirthDay;
        Main.getUserRegistry(getUsername()).register(this);
    }

    public int getBirthMonth() {
        return m_BirthMonth;
    }

    public void setBirthMonth(int p_BirthMonth) {
        this.m_BirthMonth = p_BirthMonth;
        Main.getUserRegistry(getUsername()).register(this);
    }

    public int getBirthYear() {
        return m_BirthYear;
    }

    public void setBirthYear(int p_BirthYear) {
        this.m_BirthYear = p_BirthYear;
        Main.getUserRegistry(getUsername()).register(this);
    }

    public int getForgotPasswordCode() {
        return m_ForgotPasswordCode;
    }

    public void setForgotPasswordCode(int p_ForgotPasswordCode) {
        this.m_ForgotPasswordCode = p_ForgotPasswordCode;
        Main.getUserRegistry(getUsername()).register(this);
    }

    public boolean isVerified() {
        return m_Verified;
    }

    public void setVerified(boolean p_Verified) {
        this.m_Verified = p_Verified;
        Main.getUserRegistry(getUsername()).register(this);
    }

    @Override
    public String getKey() {
        return m_Key;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("email", m_Email);
        map.put("first-name", m_FirstName);
        map.put("last-name", m_LastName);
        map.put("password-hash", m_PasswordHash);
        map.put("profile-picture-url", m_ProfilePictureUrl);
        map.put("username", m_Username);

        map.put("birth-day", m_BirthDay);
        map.put("birth-month", m_BirthMonth);
        map.put("birth-year", m_BirthYear);

        map.put("verified", m_Verified);
        return map;
    }
}
