package vn.asiantech.internship.day26;

/**
 * Created by at-dinhvo on 10/07/2017.
 */

public class User {

    private String mUserName;
    private String mPassword;

    public User(String mUserName, String mPassword) {
        this.mUserName = mUserName;
        this.mPassword = mPassword;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}
