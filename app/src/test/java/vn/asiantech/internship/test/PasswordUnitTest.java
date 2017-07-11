package vn.asiantech.internship.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.unittest.PasswordValidation;
import vn.asiantech.internship.unittest.User;

/**
 * Author AsianTech Inc.
 * Created by sony on 11/07/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class PasswordUnitTest {

    @Mock
    private User mUser;

    @Test
    public void checkPasswordLength() {
        Mockito.when(mUser.getPassword()).thenReturn("1234");
        Assert.assertTrue(PasswordValidation.checkPasswordLength(mUser.getPassword()));
        Mockito.when(mUser.getPassword()).thenReturn("123");
        Assert.assertFalse(PasswordValidation.checkPasswordLength(mUser.getPassword()));
    }

    @Test
    public void checkPasswordSpace() {
        Mockito.when(mUser.getPassword()).thenReturn("abcd");
        Assert.assertTrue(PasswordValidation.checkPasswordSpace(mUser.getPassword()));
        Mockito.when(mUser.getPassword()).thenReturn("ab cd");
        Assert.assertFalse(PasswordValidation.checkPasswordSpace(mUser.getPassword()));
    }

    @Test
    public void checkPasswordRequirement() {
        Mockito.when(mUser.getPassword()).thenReturn("Abc123*");
        Assert.assertTrue(PasswordValidation.checkPasswordRequirement(mUser.getPassword()));
        Mockito.when(mUser.getPassword()).thenReturn("abcd");
        Assert.assertFalse(PasswordValidation.checkPasswordRequirement(mUser.getPassword()));
        Mockito.when(mUser.getPassword()).thenReturn("1234");
        Assert.assertFalse(PasswordValidation.checkPasswordRequirement(mUser.getPassword()));
        Mockito.when(mUser.getPassword()).thenReturn("abcd1234");
        Assert.assertFalse(PasswordValidation.checkPasswordRequirement(mUser.getPassword()));
        Mockito.when(mUser.getPassword()).thenReturn("abcd1234*");
        Assert.assertFalse(PasswordValidation.checkPasswordRequirement(mUser.getPassword()));
    }

    @Test
    public void checkPasswordDifferentWithUser() {
        Mockito.when(mUser.getPassword()).thenReturn("abcde");
        Assert.assertTrue(PasswordValidation.checkPasswordDifferentWithUserName(mUser.getPassword(), "abcd"));
        Mockito.when(mUser.getPassword()).thenReturn("abcde");
        Assert.assertFalse(PasswordValidation.checkPasswordDifferentWithUserName(mUser.getPassword(), "abcde"));
    }
}
