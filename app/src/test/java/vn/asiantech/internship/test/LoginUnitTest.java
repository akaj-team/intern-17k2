package vn.asiantech.internship.test;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.day26.Login;
import vn.asiantech.internship.day26.User;

import static org.mockito.Mockito.when;

/**
 * Created by at-dinhvo on 10/07/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginUnitTest {

    @Mock
    private User mUser;

    @Spy
    private Login mLogin;

    @Test
    public void checkUserNameLength() {
        when(mUser.getmUserName()).thenReturn("helloWorld");
        Assert.assertTrue(mLogin.checkUserNameLength(mUser.getmUserName()));
    }

    @Test
    public void checkIgnoreUpperCase() {
        when(mUser.getmUserName()).thenReturn("helloworld");
        Assert.assertTrue(mLogin.checkUpperCase(mUser.getmUserName(), "heLLoWorld"));
    }

    @Test
    public void checkUserType() {
        when(mUser.getmUserName()).thenReturn("helloWorld777");
        Assert.assertTrue(mLogin.checkTypeUserName(mUser.getmUserName()));
    }

    @Test
    public void checkPasswordLength() {
        when(mUser.getmPassword()).thenReturn("PhiLongTaiThien2017!!");
        Assert.assertTrue(mLogin.checkPasswordLength(mUser.getmPassword()));
    }

    @Test
    public void checkSpecialCharacter() {
        when(mUser.getmPassword()).thenReturn("Philongtaithien1119%");
        Assert.assertTrue(mLogin.checkSpecialCharacter(mUser.getmPassword()));
    }

    @Test
    public void checkCountNumber() {
        when(mUser.getmPassword()).thenReturn("Philongtaithien3*");
        Assert.assertTrue(mLogin.checkNumber(mUser.getmPassword()));
    }

    @Test
    public void checkUpperCase() {
        when(mUser.getmPassword()).thenReturn("Philongtaithien");
        Assert.assertTrue(mLogin.checkPasswordUpperCase(mUser.getmPassword()));
    }

    @Test
    public void checkPasswordType() {
        when(mUser.getmPassword()).thenReturn("Philongtaithien3339/");
        Assert.assertTrue(mLogin.checkTypePassword(mUser.getmPassword()));
    }
}
