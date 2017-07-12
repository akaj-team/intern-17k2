package vn.asiantech.internship.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.day31.Login;
import vn.asiantech.internship.day31.User;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 11/07/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginUnitTest {

    @Mock
    private User mUser;

    @Test
    public void checkUserName(){
        // Check length of username
        Mockito.when(mUser.getName()).thenReturn("viet");
        Assert.assertFalse(Login.checkLengthOfUsername(mUser.getName()));
        Mockito.when(mUser.getName()).thenReturn("viet nam dat nuoc con nguoi");
        Assert.assertFalse(Login.checkLengthOfUsername(mUser.getName()));
        Mockito.when(mUser.getName()).thenReturn("vietnam");
        Assert.assertTrue(Login.checkLengthOfUsername(mUser.getName()));
        Mockito.when(mUser.getName()).thenReturn("vietnamtuoidep");
        Assert.assertTrue(Login.checkLengthOfUsername(mUser.getName()));

        // Check space of username
        Mockito.when(mUser.getName()).thenReturn("viet nam");
        Assert.assertTrue(Login.checkSpaceOfUsername(mUser.getName()));
        Mockito.when(mUser.getName()).thenReturn(" vietnam");
        Assert.assertTrue(Login.checkSpaceOfUsername(mUser.getName()));
        Mockito.when(mUser.getName()).thenReturn("vietnam ");
        Assert.assertTrue(Login.checkSpaceOfUsername(mUser.getName()));
        Mockito.when(mUser.getName()).thenReturn("vietnam");
        Assert.assertFalse(Login.checkSpaceOfUsername(mUser.getName()));

        // Check just alphabe
        Mockito.when(mUser.getName()).thenReturn("vietnam");
        Assert.assertTrue(Login.checkAlphabeOfUsername(mUser.getName()));
        Mockito.when(mUser.getName()).thenReturn("vietnam1234");
        Assert.assertFalse(Login.checkSpaceOfUsername(mUser.getName()));
        Mockito.when(mUser.getName()).thenReturn("vietnam_@#1^");
        Assert.assertFalse(Login.checkSpaceOfUsername(mUser.getName()));
        Mockito.when(mUser.getName()).thenReturn("VietNam");
        Assert.assertFalse(Login.checkSpaceOfUsername(mUser.getName()));
    }

    @Test
    public void checkPassword(){
        // Check length of password
        Mockito.when(mUser.getPassword()).thenReturn("datnuoc");
        Assert.assertTrue(Login.checkLengthOfPassword(mUser.getPassword()));
        Mockito.when(mUser.getPassword()).thenReturn("vn");
        Assert.assertFalse(Login.checkLengthOfPassword(mUser.getPassword()));

        // Check space of password
        Mockito.when(mUser.getPassword()).thenReturn("datnuoc");
        Assert.assertFalse(Login.checkSpaceOfPassword(mUser.getPassword()));
        Mockito.when(mUser.getPassword()).thenReturn("dat nuoc");
        Assert.assertTrue(Login.checkSpaceOfPassword(mUser.getPassword()));
        Mockito.when(mUser.getPassword()).thenReturn(" datnuoc");
        Assert.assertTrue(Login.checkSpaceOfPassword(mUser.getPassword()));
        Mockito.when(mUser.getPassword()).thenReturn("datnuoc ");
        Assert.assertTrue(Login.checkSpaceOfPassword(mUser.getPassword()));

        // Check not same user
        Mockito.when(mUser.getName()).thenReturn("vietnam");
        Mockito.when(mUser.getPassword()).thenReturn("datnuoc");
        Assert.assertFalse(Login.checkNotSameUsername(mUser.getPassword(),mUser.getName()));
        Mockito.when(mUser.getName()).thenReturn("vietnam");
        Mockito.when(mUser.getPassword()).thenReturn("vietnam");
        Assert.assertTrue(Login.checkNotSameUsername(mUser.getPassword(),mUser.getName()));

        // Check have at least 1 UpperCase + 1 number + 1 special letter
        Mockito.when(mUser.getPassword()).thenReturn("Vietnam123@");
        Assert.assertTrue(Login.checkUpperNumberSpecial(mUser.getPassword()));
        Mockito.when(mUser.getPassword()).thenReturn("vietnam123@");
        Assert.assertFalse(Login.checkUpperNumberSpecial(mUser.getPassword()));
        Mockito.when(mUser.getPassword()).thenReturn("Vietnam123");
        Assert.assertFalse(Login.checkUpperNumberSpecial(mUser.getPassword()));
        Mockito.when(mUser.getPassword()).thenReturn("Vietnam@");
        Assert.assertFalse(Login.checkUpperNumberSpecial(mUser.getPassword()));
    }
}
