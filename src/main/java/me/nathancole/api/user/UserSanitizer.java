package me.nathancole.api.user;

public class UserSanitizer {

    private String userId;
    private String username;
    private String email;
    private String birthday;

    private boolean verified;

    public UserSanitizer(UserModel userModel) {
        this.userId = userModel.getKey();
        this.username = userModel.getUsername();
        this.email = userModel.getEmail();
        this.birthday = userModel.getBirthMonth() + "/" + userModel.getBirthDay() + "/" + userModel.getBirthYear();
        this.verified = userModel.isVerified();
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthday() {
        return birthday;
    }

    public boolean isVerified() {
        return verified;
    }
}
