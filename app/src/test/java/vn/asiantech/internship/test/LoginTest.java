package vn.asiantech.internship.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.unittest.Account;
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
    private Account mAccount;

    @Test
    public void checkUserLength() {
        //Length < 6
        String s = "01234";
        Mockito.when(mAccount.getUser()).thenReturn(s);
        assertFalse(Login.checkUserLength(mAccount.getUser()));

        //Length = 6
        s = "012345";
        Mockito.when(mAccount.getUser()).thenReturn(s);
        assertTrue(Login.checkUserLength(mAccount.getUser()));

        //6 < Length < 24
        s = "0123456789";
        Mockito.when(mAccount.getUser()).thenReturn(s);
        assertTrue(Login.checkUserLength(mAccount.getUser()));

        //Length = 24
        s = "012345678901234567890123";
        Mockito.when(mAccount.getUser()).thenReturn(s);
        assertTrue(Login.checkUserLength(mAccount.getUser()));

        //Length > 24
        s = "012345678901234567890123456";
        Mockito.when(mAccount.getUser()).thenReturn(s);
        assertFalse(Login.checkUserLength(mAccount.getUser()));
    }

    @Test
    public void checkUserAlphaNumber() {
        //Correct
        String s = "65asd232asd65";
        Mockito.when(mAccount.getUser()).thenReturn(s);
        assertTrue(Login.checkUserAlphaNumber(mAccount.getUser()));

        //Have space
        s = "asdasdk 656565 ";
        Mockito.when(mAccount.getUser()).thenReturn(s);
        assertFalse(Login.checkUserAlphaNumber(mAccount.getUser()));

        //Have SpecialChar
        s = "asdasdk#@656565";
        Mockito.when(mAccount.getUser()).thenReturn(s);
        assertFalse(Login.checkUserAlphaNumber(mAccount.getUser()));
    }

    @Test
    public void checkPassLength() {
        //Length < 4
        String s = "012";
        Mockito.when(mAccount.getPass()).thenReturn(s);
        assertFalse(Login.checkPassLength(mAccount.getPass()));

        //Length = 4
        s = "0123";
        Mockito.when(mAccount.getPass()).thenReturn(s);
        assertTrue(Login.checkPassLength(mAccount.getPass()));

        //Length > 4
        s = "0123456789";
        Mockito.when(mAccount.getPass()).thenReturn(s);
        assertTrue(Login.checkPassLength(mAccount.getPass()));
    }

    @Test
    public void checkPassStrenght() {
        //Not have number and special char
        String s = "asdhkjhad";
        Mockito.when(mAccount.getPass()).thenReturn(s);
        assertFalse(Login.checkPassStrength(mAccount.getPass()));

        //Not have special char
        s = "asdhksa12jhad";
        Mockito.when(mAccount.getPass()).thenReturn(s);
        assertFalse(Login.checkPassStrength(mAccount.getPass()));

        //Not have number
        s = "asdhkjhad@$^";
        Mockito.when(mAccount.getPass()).thenReturn(s);
        assertFalse(Login.checkPassStrength(mAccount.getPass()));

        //All number
        s = "653232653";
        Mockito.when(mAccount.getPass()).thenReturn(s);
        assertFalse(Login.checkPassStrength(mAccount.getPass()));

        //Not have simple char
        s = "54155%$&";
        Mockito.when(mAccount.getPass()).thenReturn(s);
        assertFalse(Login.checkPassStrength(mAccount.getPass()));

        //All special char
        s = "$^&&$$%^&$%&";
        Mockito.when(mAccount.getPass()).thenReturn(s);
        assertFalse(Login.checkPassStrength(mAccount.getPass()));

        //Invalidate
        s = "45aSd^%^%sad";
        Mockito.when(mAccount.getPass()).thenReturn(s);
        assertTrue(Login.checkPassStrength(mAccount.getPass()));
    }

    @Test
    public void checkPassDifferent() {
        //Same
        String user = "cuongcao";
        String pass = "cuongcao";
        Mockito.when(mAccount.getUser()).thenReturn(user);
        Mockito.when(mAccount.getPass()).thenReturn(pass);
        assertFalse(Login.checkPassDifferentUser(mAccount.getUser(), mAccount.getPass()));

        //Different
        user = "at-cuongcao";
        pass = "cuongcao";
        Mockito.when(mAccount.getUser()).thenReturn(user);
        Mockito.when(mAccount.getPass()).thenReturn(pass);
        assertTrue(Login.checkPassDifferentUser(mAccount.getUser(), mAccount.getPass()));
    }
}
