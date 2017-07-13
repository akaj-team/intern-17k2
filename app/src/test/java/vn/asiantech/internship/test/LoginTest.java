package vn.asiantech.internship.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.unittest.Login;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 7/11/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginTest {

    @Mock
    private Login mLogin;

    @Test
    public void checkUserLength() {
        //Length < 6
        String s = "01234";
        Mockito.when(mLogin.getUser()).thenReturn(s);
        assertFalse(mLogin.checkUserLength(mLogin.getUser()));

        //Length = 6
        s = "012345";
        Mockito.when(mLogin.getUser()).thenReturn(s);
        assertTrue(Login.checkUserLength(mLogin.getUser()));

        //6 < Length < 24
        s = "0123456789";
        Mockito.when(mLogin.getUser()).thenReturn(s);
        assertTrue(Login.checkUserLength(mLogin.getUser()));

        //Length = 24
        s = "012345678901234567890123";
        Mockito.when(mLogin.getUser()).thenReturn(s);
        assertTrue(Login.checkUserLength(mLogin.getUser()));

        //Length > 24
        s = "012345678901234567890123456";
        Mockito.when(mLogin.getUser()).thenReturn(s);
        assertFalse(Login.checkUserLength(mLogin.getUser()));
    }

    @Test
    public void checkUserAlphaNumber() {
        //Correct
        String s = "65asd232asd65";
        Mockito.when(mLogin.getUser()).thenReturn(s);
        assertTrue(Login.checkUserAlphaNumber(mLogin.getUser()));

        //Have space
        s = "asdasdk 656565 ";
        Mockito.when(mLogin.getUser()).thenReturn(s);
        assertFalse(Login.checkUserAlphaNumber(mLogin.getUser()));

        //Have SpecialChar
        s = "asdasdk#@656565";
        Mockito.when(mLogin.getUser()).thenReturn(s);
        assertFalse(Login.checkUserAlphaNumber(mLogin.getUser()));
    }

    @Test
    public void checkPassLength() {
        //Length < 4
        String s = "012";
        Mockito.when(mLogin.getPass()).thenReturn(s);
        assertFalse(Login.checkPassLength(mLogin.getPass()));

        //Length = 4
        s = "0123";
        Mockito.when(mLogin.getPass()).thenReturn(s);
        assertTrue(Login.checkPassLength(mLogin.getPass()));

        //Length > 4
        s = "0123456789";
        Mockito.when(mLogin.getPass()).thenReturn(s);
        assertTrue(Login.checkPassLength(mLogin.getPass()));
    }

    @Test
    public void checkPassStrenght() {
        //Not have number and special char
        String s = "asdhkjhad";
        Mockito.when(mLogin.getPass()).thenReturn(s);
        assertFalse(Login.checkPassStrength(mLogin.getPass()));

        //Not have special char
        s = "asdhksa12jhad";
        Mockito.when(mLogin.getPass()).thenReturn(s);
        assertFalse(Login.checkPassStrength(mLogin.getPass()));

        //Not have number
        s = "asdhkjhad@$^";
        Mockito.when(mLogin.getPass()).thenReturn(s);
        assertFalse(Login.checkPassStrength(mLogin.getPass()));

        //All number
        s = "653232653";
        Mockito.when(mLogin.getPass()).thenReturn(s);
        assertFalse(Login.checkPassStrength(mLogin.getPass()));

        //Not have simple char
        s = "54155%$&";
        Mockito.when(mLogin.getPass()).thenReturn(s);
        assertFalse(Login.checkPassStrength(mLogin.getPass()));

        //All special char
        s = "$^&&$$%^&$%&";
        Mockito.when(mLogin.getPass()).thenReturn(s);
        assertFalse(Login.checkPassStrength(mLogin.getPass()));

        //Invalidate
        s = "45aSd^%^%sad";
        Mockito.when(mLogin.getPass()).thenReturn(s);
        assertTrue(Login.checkPassStrength(mLogin.getPass()));
    }

    @Test
    public void checkPassDifferent() {
        //Same
        String user = "cuongcao";
        String pass = "cuongcao";
        Mockito.when(mLogin.getUser()).thenReturn(user);
        Mockito.when(mLogin.getPass()).thenReturn(pass);
        assertFalse(Login.checkPassDifferentUser(mLogin.getUser(), mLogin.getPass()));

        //Different
        user = "at-cuongcao";
        pass = "cuongcao";
        Mockito.when(mLogin.getUser()).thenReturn(user);
        Mockito.when(mLogin.getPass()).thenReturn(pass);
        assertTrue(Login.checkPassDifferentUser(mLogin.getUser(), mLogin.getPass()));
    }
}
