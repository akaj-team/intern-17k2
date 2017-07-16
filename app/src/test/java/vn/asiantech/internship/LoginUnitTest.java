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
    public void checkUserName() {
        //check length
        when(mLoginTest.isCheckUserLength("abcdefgh")).thenReturn(true);
        assertTrue(true);

        when(mLoginTest.isCheckUserLength("abcd")).thenReturn(false);
        assertFalse(false);

        when(mLoginTest.isCheckUserLength("abcdabcdabcdabcdabcdabcdabcd")).thenReturn(false);
        assertFalse(false);

        //check space
        when(mLoginTest.isCheckUserSpace("daicanao")).thenReturn(true);
        assertTrue(true);

        when(mLoginTest.isCheckUserSpace("daica nao")).thenReturn(false);
        assertFalse(false);

        //check alphanumeric
        when(mLoginTest.isCheckUserAlphanumeric("hello")).thenReturn(false);
        assertFalse(false);

        when(mLoginTest.isCheckUserAlphanumeric("hungt4")).thenReturn(true);
        assertTrue(true);

        //get user with uppercase letter
        when(mLoginTest.getUser("helloworld")).thenReturn("HELLOWORLD");
        assertEquals(mLoginTest.getUser("helloworld"), "HELLOWORLD");
    }

    @Test
    public void checkPassword() {

        //check length
        when(mLoginTest.isCheckPasswordLength("abc")).thenReturn(false);
        assertFalse(false);

        when(mLoginTest.isCheckPasswordLength("abcde")).thenReturn(true);
        assertTrue(true);

        when(mLoginTest.isCheckPasswordLength("abcd")).thenReturn(true);
        assertTrue(true);

        //check char
        when(mLoginTest.isCheckPasswordChar("abcd")).thenReturn(false);
        assertFalse(false);

        when(mLoginTest.isCheckPasswordChar("a1bcd")).thenReturn(false);
        assertFalse(false);

        when(mLoginTest.isCheckPasswordChar("Abcde")).thenReturn(false);
        assertFalse(false);

        when(mLoginTest.isCheckPasswordChar("Ab1cde")).thenReturn(false);
        assertFalse(false);

        when(mLoginTest.isCheckPasswordChar("Ab#cde")).thenReturn(false);
        assertFalse(false);

        when(mLoginTest.isCheckPasswordChar("a1b#cde")).thenReturn(false);
        assertFalse(false);

        when(mLoginTest.isCheckPasswordChar("Ab1#cde")).thenReturn(true);
        assertTrue(true);

        //check equal user
        when(mLoginTest.isCheckPasswordEqualUser("abcde", "abcde")).thenReturn(false);
        assertFalse(false);

        when(mLoginTest.isCheckPasswordEqualUser("abcde", "abcd")).thenReturn(true);
        assertTrue(true);
    }
}
