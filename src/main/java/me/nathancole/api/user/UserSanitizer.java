package me.nathancole.api.user;

import com.google.common.collect.Lists;

import java.util.List;

public class UserSanitizer {

    private String userId;
    private String username;
    private String email;
    private String birthday;
    private String profilePictureUrl;
    private String name;

    private boolean verified;

    private List<String> followers;
    private List<String> following;
    private List<String> followRequests;

    public UserSanitizer(UserModel userModel) {
        this.userId = userModel.getKey();
        this.username = userModel.getUsername();
        this.email = userModel.getEmail();
        this.birthday = userModel.getBirthMonth() + "/" + userModel.getBirthDay() + "/" + userModel.getBirthYear();
        this.profilePictureUrl = userModel.getProfilePictureUrl();
        this.name = userModel.getFirstName() + " " + userModel.getLastName();

        this.verified = userModel.isVerified();

        followers = Lists.newArrayList();
        userModel.getFollowers().forEach(user -> followers.add(user.getKey()));
        following = Lists.newArrayList();
        userModel.getFollowing().forEach(user -> following.add(user.getKey()));
        followRequests = Lists.newArrayList();
        userModel.getFollowRequest().forEach(user -> followRequests.add(user.getKey()));
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

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public String getName() {
        return name;
    }

    public boolean isVerified() {
        return verified;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public List<String> getFollowing() {
        return following;
    }

    public List<String> getFollowRequests() {
        return followRequests;
    }
}