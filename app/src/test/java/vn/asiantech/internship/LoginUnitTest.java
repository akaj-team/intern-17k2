package vn.asiantech.internship;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.unittest.LoginTest;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by ducle on 12/07/2017.
 * LoginUnitTest: test user and password
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class LoginUnitTest {
    @Mock
    private LoginTest mLoginTest;

    @Test
    public void checkUserNameLength() {
        when(mLoginTest.isCheckUserLength("abcdefgh")).thenReturn(true);
        assertTrue(mLoginTest.isCheckUserLength("abcdefgh"));

        when(mLoginTest.isCheckUserLength("abcd")).thenReturn(false);
        assertFalse(mLoginTest.isCheckUserLength("abcd"));

        when(mLoginTest.isCheckUserLength("abcdabcdabcdabcdabcdabcdabcd")).thenReturn(false);
        assertFalse(mLoginTest.isCheckUserLength("abcdabcdabcdabcdabcdabcdabcd"));
    }

    @Test
    public void checkUserNameSpace() {
        when(mLoginTest.isCheckUserSpace("daicanao")).thenReturn(true);
        assertTrue(mLoginTest.isCheckUserSpace("daicanao"));

        when(mLoginTest.isCheckUserSpace("daica nao")).thenReturn(false);
        assertFalse(mLoginTest.isCheckUserSpace("daica nao"));
    }

    @Test
    public void checkUserNameAlphanumeric() {
        when(mLoginTest.isCheckUserAlphanumeric("hello")).thenReturn(false);
        assertFalse(mLoginTest.isCheckUserAlphanumeric("hello"));

        when(mLoginTest.isCheckUserAlphanumeric("hungt4")).thenReturn(true);
        assertTrue(mLoginTest.isCheckUserAlphanumeric("hungt4"));
    }

    @Test
    public void checkUserNameUppercaseLetter() {
        //get user with uppercase letter
        when(mLoginTest.getUser("hello_world")).thenReturn("HELLO_WORLD");
        assertEquals("HELLO_WORLD", mLoginTest.getUser("hello_world"));
    }

    @Test
    public void checkPasswordLength() {
        //check length
        when(mLoginTest.isCheckPasswordLength("abc")).thenReturn(false);
        assertFalse(mLoginTest.isCheckPasswordLength("abc"));

        when(mLoginTest.isCheckPasswordLength("abcde")).thenReturn(true);
        assertTrue(mLoginTest.isCheckPasswordLength("abcde"));

        when(mLoginTest.isCheckPasswordLength("abcd")).thenReturn(true);
        assertTrue(mLoginTest.isCheckPasswordLength("abcd"));
    }

    @Test
    public void checkPasswordChar() {
        //check char
        when(mLoginTest.isCheckPasswordChar("abcd")).thenReturn(false);
        assertFalse(mLoginTest.isCheckPasswordChar("abcd"));

        when(mLoginTest.isCheckPasswordChar("a1bcd")).thenReturn(false);
        assertFalse(mLoginTest.isCheckPasswordChar("a1bcd"));

        when(mLoginTest.isCheckPasswordChar("Abcde")).thenReturn(false);
        assertFalse(mLoginTest.isCheckPasswordChar("Abcde"));

        when(mLoginTest.isCheckPasswordChar("Ab1cde")).thenReturn(false);
        assertFalse(mLoginTest.isCheckPasswordChar("Ab1cde"));

        when(mLoginTest.isCheckPasswordChar("Ab#cde")).thenReturn(false);
        assertFalse(mLoginTest.isCheckPasswordChar("Ab#cde"));

        when(mLoginTest.isCheckPasswordChar("a1b#cde")).thenReturn(false);
        assertFalse(mLoginTest.isCheckPasswordChar("a1b#cde"));

        when(mLoginTest.isCheckPasswordChar("Ab1#cde")).thenReturn(true);
        assertTrue(mLoginTest.isCheckPasswordChar("Ab1#cde"));
    }

    @Test
    public void checkPasswordEqualUser() {
        //check equal user
        when(mLoginTest.isCheckPasswordEqualUser("abcde", "abcde")).thenReturn(false);
        assertFalse(mLoginTest.isCheckPasswordEqualUser("abcde", "abcde"));

        when(mLoginTest.isCheckPasswordEqualUser("abcde", "abcd")).thenReturn(true);
        assertTrue(mLoginTest.isCheckPasswordEqualUser("abcde", "abcd"));
    }
}
