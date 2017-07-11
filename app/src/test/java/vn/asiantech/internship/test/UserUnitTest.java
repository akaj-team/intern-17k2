package vn.asiantech.internship.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.unittest.User;
import vn.asiantech.internship.unittest.UserNameValidation;

/**
 * Author AsianTech Inc.
 * Created by sony on 10/07/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserUnitTest {

    @Mock
    private User mUser;

    @Test
    public void checkUserNameLength() {
        Mockito.when(mUser.getUserName()).thenReturn("hangtran");
        Assert.assertTrue(UserNameValidation.checkUserNameLength(mUser.getUserName()));
        Mockito.when(mUser.getUserName()).thenReturn("hoa");
        Assert.assertFalse(UserNameValidation.checkUserNameLength(mUser.getUserName()));
    }

    @Test
    public void checkUserNameSpace() {
        Mockito.when(mUser.getUserName()).thenReturn("hangtran");
        Assert.assertTrue(UserNameValidation.checkUserNameSpace(mUser.getUserName()));
        Mockito.when(mUser.getUserName()).thenReturn("hang tran");
        Assert.assertFalse(UserNameValidation.checkUserNameSpace(mUser.getUserName()));
    }

    @Test
    public void checkUserNameCharacter() {
        Mockito.when(mUser.getUserName()).thenReturn("123abc");
        Assert.assertTrue(UserNameValidation.checkUserNameCharacter(mUser.getUserName()));
        Mockito.when(mUser.getUserName()).thenReturn("hangtran@%*@");
        Assert.assertFalse(UserNameValidation.checkUserNameCharacter(mUser.getUserName()));
    }

    @Test
    public void checkUserNameIgnoreCase() {
        Mockito.when(mUser.getUserName()).thenReturn("abcd");
        Assert.assertTrue(UserNameValidation.checkUserNameIgnoreUpperCase(mUser.getUserName()));
        Mockito.when(mUser.getUserName()).thenReturn("Abcd");
        Assert.assertFalse(UserNameValidation.checkUserNameIgnoreUpperCase(mUser.getUserName()));
    }
}
