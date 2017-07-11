package vn.asiantech.internship.test;

import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Test
    public void checkUserLength() {
        //Length < 6
        String s = "01234";
        assertFalse(Login.checkUserLength(s));

        //Length = 6
        s = "012345";
        assertTrue(Login.checkUserLength(s));

        //6 < Length < 24
        s = "0123456789";
        assertTrue(Login.checkUserLength(s));

        //Length = 24
        s = "012345678901234567890123";
        assertTrue(Login.checkUserLength(s));

        //Length > 24
        s = "012345678901234567890123456";
        assertFalse(Login.checkUserLength(s));
    }

    @Test
    public void checkUserAlphaNumber() {
        //Correct
        String s = "65asd232asd65";
        assertTrue(Login.checkUserAlphaNumber(s));

        //Have space
        s = "asdasdk 656565 ";
        assertFalse(Login.checkUserAlphaNumber(s));

        //Have SpecialChar
        s = "asdasdk#@656565";
        assertFalse(Login.checkUserAlphaNumber(s));
    }

    @Test
    public void checkPassLength() {
        //Length < 4
        String s = "012";
        assertFalse(Login.checkPassLength(s));

        //Length = 4
        s = "0123";
        assertTrue(Login.checkPassLength(s));

        //Length > 4
        s = "0123456789";
        assertTrue(Login.checkPassLength(s));
    }

    @Test
    public void checkPassStrenght() {
        //Not have number and special char
        String s = "asdhkjhad";
        assertFalse(Login.checkPassStrength(s));

        //Not have special char
        s = "asdhksa12jhad";
        assertFalse(Login.checkPassStrength(s));

        //Not have number
        s = "asdhkjhad@$^";
        assertFalse(Login.checkPassStrength(s));

        //All number
        s = "653232653";
        assertFalse(Login.checkPassStrength(s));

        //Not have simple char
        s = "54155%$&";
        assertFalse(Login.checkPassStrength(s));

        //All special char
        s = "$^&&$$%^&$%&";
        assertFalse(Login.checkPassStrength(s));

        //Invalidate
        s = "45aSd^%^%sad";
        assertTrue(Login.checkPassStrength(s));
    }

    @Test
    public void checkPassDifferent() {
        //Same
        String user = "cuongcao";
        String pass = "cuongcao";
        assertFalse(Login.checkPassDifferentUser(user, pass));

        //Different
        user = "at-cuongcao";
        pass = "cuongcao";
        assertTrue(Login.checkPassDifferentUser(user, pass));
    }
}
