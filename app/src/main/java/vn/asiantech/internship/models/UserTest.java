package vn.asiantech.internship.models;

/**
 * Created by quanghai on 11/07/2017.
 */
public class UserTest {
    private String userName;
    private String password;

    public UserTest() {
    }

    public UserTest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
