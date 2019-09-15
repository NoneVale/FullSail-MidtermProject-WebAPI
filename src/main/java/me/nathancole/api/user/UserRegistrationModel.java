package me.nathancole.api.user;

public class UserRegistrationModel {

    private String m_Email;
    private String m_FirstName;
    private String m_LastName;
    private String m_Username;
    private String m_Password;

    private int m_BirthDay;
    private int m_BirthMonth;
    private int m_BirthYear;

    public UserRegistrationModel(String p_Email, String p_FirstName, String p_LastName, String p_Username, String p_Password,
                                 int p_BirthDay, int p_BirthMonth, int p_BirthYear) {
        this.m_Email = p_Email;
        this.m_FirstName = p_FirstName;
        this.m_LastName = p_LastName;
        this.m_Username = p_Username;
        this.m_Password = p_Password;

        this.m_BirthDay = p_BirthDay;
        this.m_BirthMonth = p_BirthMonth;
        this.m_BirthYear = p_BirthYear;
    }

    public String getEmail() {
        return m_Email;
    }

    public String getFirstName() {
        return m_FirstName;
    }

    public String getLastName() {
        return m_LastName;
    }

    public String getUsername() {
        return m_Username;
    }

    public String getPassword() {
        return m_Password;
    }

    public int getBirthDay() {
        return m_BirthDay;
    }

    public int getBirthMonth() {
        return m_BirthMonth;
    }

    public int getBirthYear () {
        return m_BirthYear;
    }
}
