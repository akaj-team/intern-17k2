package vn.asiantech.internship.ui.testcase;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Thanh Thien on 7/10/2017.
 * User
 */
public class User implements Parcelable {
    private String userName;
    private String passWord;

    private User(Parcel in) {
        userName = in.readString();
        passWord = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(passWord);
    }
}
