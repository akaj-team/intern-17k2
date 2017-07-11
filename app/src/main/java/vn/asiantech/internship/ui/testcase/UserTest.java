package vn.asiantech.internship.ui.testcase;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Thanh Thien on 7/10/2017.
 * UserTest
 */
public class UserTest implements Parcelable {
    private String userName;
    private String passWord;

    private UserTest(Parcel in) {
        userName = in.readString();
        passWord = in.readString();
    }

    public static final Creator<UserTest> CREATOR = new Creator<UserTest>() {
        @Override
        public UserTest createFromParcel(Parcel in) {
            return new UserTest(in);
        }

        @Override
        public UserTest[] newArray(int size) {
            return new UserTest[size];
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

    public UserTest(String userName, String passWord) {
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
