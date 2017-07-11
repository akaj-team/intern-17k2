package vn.asiantech.internship.test;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

    private Login mLogin = new Login();

    @Test
    public void checkUserNameLength() {
        when(mUser.getUserName()).thenReturn("helloWorld");
        Assert.assertTrue(mLogin.checkUserNameLength(mUser.getUserName()));
    }

    @Test
    public void checkIgnoreUpperCase() {
        when(mUser.getUserName()).thenReturn("helloworld");
        Assert.assertTrue(mLogin.checkUpperCase(mUser.getUserName(), "heLLoWorld"));
    }

    @Test
    public void checkUserType() {
        when(mUser.getUserName()).thenReturn("helloWorld777");
        Assert.assertTrue(mLogin.checkTypeUserName(mUser.getUserName()));
    }

    @Test
    public void checkPasswordLength() {
        when(mUser.getPassword()).thenReturn("PhiLongTaiThien2017!!");
        Assert.assertTrue(mLogin.checkPasswordLength(mUser.getPassword()));
    }

    @Test
    public void checkSpecialCharacter() {
        when(mUser.getPassword()).thenReturn("Philongtaithien1119%");
        Assert.assertTrue(mLogin.checkSpecialCharacter(mUser.getPassword()));
    }

    @Test
    public void checkCountNumber() {
        when(mUser.getPassword()).thenReturn("Philongtaithien3*");
        Assert.assertTrue(mLogin.checkNumber(mUser.getPassword()));
    }

    @Test
    public void checkUpperCase() {
        when(mUser.getPassword()).thenReturn("Philongtaithien");
        Assert.assertTrue(mLogin.checkPasswordUpperCase(mUser.getPassword()));
    }

    @Test
    public void checkPasswordType() {
        when(mUser.getPassword()).thenReturn("Philongtaithien3339/");
        Assert.assertTrue(mLogin.checkTypePassword(mUser.getPassword()));
    }
}
