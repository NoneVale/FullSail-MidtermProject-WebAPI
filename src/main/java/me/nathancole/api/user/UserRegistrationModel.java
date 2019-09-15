package me.nathancole.api.user;


public class UserRegistrationModel {

    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    private int birthDay;
    private int birthMonth;
    private int birthYear;

    public UserRegistrationModel(String email, String firstName, String lastName, String username, String password,
                                 int birthDay, int birthMonth, int birthYear) {
        System.out.println(email);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;

        System.out.println(birthDay);
        this.birthDay = birthDay;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getBirthDay() {
        return birthDay;
    }

    public int getBirthMonth() {
        return birthMonth;
    }

    public int getBirthYear () {
        return birthYear;
    }
}
