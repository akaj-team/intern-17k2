package vn.asiantech.internship.unittest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.models.Login;
import vn.asiantech.internship.others.LoginValidation;

/**
 * Login Unit Test With Mockito.
 * Created by huypham on 18-Jul-17.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginTest {

    @Mock
    private Login mLogin;

    @Test
    public void testUserLength() {
        Mockito.when(mLogin.getUser()).thenReturn("huypham");
        Assert.assertTrue(LoginValidation.checkUserLength(mLogin.getUser()));
        Mockito.when(mLogin.getUser()).thenReturn("huy");
        Assert.assertFalse(LoginValidation.checkUserLength(mLogin.getUser()));
    }

    @Test
    public void testUserCharacter() {
        Mockito.when(mLogin.getUser()).thenReturn("HuyPham173");
        Assert.assertTrue(LoginValidation.checkUserCharacter(mLogin.getUser()));
        Mockito.when(mLogin.getUser()).thenReturn("Huy@$17");
        Assert.assertFalse(LoginValidation.checkUserCharacter(mLogin.getUser()));
    }

    @Test
    public void testPasswordLength() {
        Mockito.when(mLogin.getPassword()).thenReturn("abcd");
        Assert.assertTrue(LoginValidation.checkPasswordLength(mLogin.getPassword()));
        Mockito.when(mLogin.getPassword()).thenReturn("xyz");
        Assert.assertFalse(LoginValidation.checkPasswordLength(mLogin.getPassword()));
    }

    @Test
    public void testPasswordCharacter() {
        Mockito.when(mLogin.getPassword()).thenReturn("aBc@@17");
        Assert.assertTrue(LoginValidation.checkPasswordCharacter(mLogin.getPassword()));
        Mockito.when(mLogin.getPassword()).thenReturn("bcdq");
        Assert.assertFalse(LoginValidation.checkPasswordCharacter(mLogin.getPassword()));
    }

    @Test
    public void testPasswordDifference() {
        Mockito.when(mLogin.getPassword()).thenReturn("huypham");
        Assert.assertFalse(LoginValidation.checkDifference(mLogin.getPassword(), "huypham"));
        Mockito.when(mLogin.getPassword()).thenReturn("huy");
        Assert.assertTrue(LoginValidation.checkDifference(mLogin.getPassword(), "huy17"));
    }

    @Test
    public void testPasswordSpace() {
        Mockito.when(mLogin.getPassword()).thenReturn("asdf");
        Assert.assertTrue(LoginValidation.checkPasswordNoSpace(mLogin.getPassword()));
        Mockito.when(mLogin.getPassword()).thenReturn("as df");
        Assert.assertFalse(LoginValidation.checkPasswordNoSpace(mLogin.getPassword()));
    }
}
