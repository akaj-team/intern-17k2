package vn.asiantech.internship;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.exday26.Login;
import vn.asiantech.internship.exday26.User;

/**
 * Created by datbu on 12-07-2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginTest {

    @Mock
    private User mUser;

    @Test
    public void checkUserLength() {
        // Case 1: Length < 6
        String s = "hello";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertFalse(Login.checkLengthUsername(mUser.getUserName()));
        // Case 2: Length = 6
        s = "helloa";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertTrue(Login.checkLengthUsername(mUser.getUserName()));
        // Case 3: 6 < Length < 24
        s = "helloHello";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertTrue(Login.checkLengthUsername(mUser.getUserName()));
        // Case 4: Length > 24
        s = "hello123456789123456789hello123";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertFalse(Login.checkLengthUsername(mUser.getUserName()));
        // Case 5: Length = 24
        s = "hello1234567890123456789";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertTrue(Login.checkLengthUsername(mUser.getUserName()));
    }

    @Test
    public void checkSpaceUserName() {
        //  case 1
        String s = "Hello";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertTrue(Login.checkSpaceUserName(mUser.getUserName()));
        // case 2
        s = "H e l l o";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertFalse(Login.checkSpaceUserName(mUser.getUserName()));
        // case 3
        s = "Hello ";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertFalse(Login.checkSpaceUserName(mUser.getUserName()));
        // case 4
        s = " Hello";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertFalse(Login.checkSpaceUserName(mUser.getUserName()));
        // case 5
        s = " Hello ";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertFalse(Login.checkSpaceUserName(mUser.getUserName()));
        // case 6
        s = " H e l l o ";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertFalse(Login.checkSpaceUserName(mUser.getUserName()));
    }

    @Test
    public void checkUserNameAlphaNumeric() {
        // Case 1: A-Z, a-z, 0-9
        String s = "Abc123456";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertTrue(Login.checkAlphaNumeric(mUser.getUserName()));
        // Case 2: a-z
        s = "zxcasdqwe";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertTrue(Login.checkAlphaNumeric(mUser.getUserName()));
        // Case 3: A-Z
        s = "ZASDSADASD";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertTrue(Login.checkAlphaNumeric(mUser.getUserName()));
        // Case 4: 0-9
        s = "1231123456";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertTrue(Login.checkAlphaNumeric(mUser.getUserName()));
        // Case 5: A-Z, a-z, 0-9, have 1 space
        s = "ASDasD A1231";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertFalse(Login.checkAlphaNumeric(mUser.getUserName()));
        // Case 6: A-Z, a-z, 0-9, have special char
        s = "zxcas123@#@#@dqwe";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertFalse(Login.checkAlphaNumeric(mUser.getUserName()));
    }

    @Test
    public void checkUserNameChar() {
        // Case 1: have upper and lower case
        String s = "AbcdE";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertTrue(Login.checkUpperCase(mUser.getUserName()));
        // Case 2: just have lower case
        s = "asdzxcqwe";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertTrue(Login.checkUpperCase(mUser.getUserName()));
        // Case 3 : just have upper case
        s = "ASDZXCQWE";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertTrue(Login.checkUpperCase(mUser.getUserName()));
        // Case 4: have upper and lower case, have space
        s = "ASD zxc";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertFalse(Login.checkUpperCase(mUser.getUserName()) && Login.checkSpaceUserName(mUser.getUserName()));
        // Case 5: have upper and lower case, have special char
        s = "ASDzxc!@#";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertFalse(Login.checkUpperCase(mUser.getUserName()) && Login.checkAlphaNumeric(mUser.getUserName()));
        // Case 6: have upper and lower case, space, special char
        s = "ASD zxc!@#";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertFalse(Login.checkUpperCase(mUser.getUserName()) && Login.checkSpaceUserName(mUser.getUserName()) && Login.checkAlphaNumeric(mUser.getUserName()));
        // Case 7: have lower case, space, special char
        s = "asd zxc @!#";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        Assert.assertFalse(Login.checkUpperCase(mUser.getUserName()) && Login.checkSpaceUserName(mUser.getUserName()) && Login.checkAlphaNumeric(mUser.getUserName()));
    }

    @Test
    public void checkPassLength() {
        // Case 1: Length > 4
        String p = "hel";
        Mockito.when(mUser.getPassWord()).thenReturn(p);
        Assert.assertFalse(Login.checkPassLength(mUser.getPassWord()));
        // Case 2: Length = 4
        p = "hell";
        Mockito.when(mUser.getPassWord()).thenReturn(p);
        Assert.assertTrue(Login.checkPassLength(mUser.getPassWord()));
        // Case 3: 4 < Length
        p = "helloHello";
        Mockito.when(mUser.getPassWord()).thenReturn(p);
        Assert.assertTrue(Login.checkPassLength(mUser.getPassWord()));
    }

    @Test
    public void checkPassInputType() {
        // Case 1: A-Z, a-z, 0-9
        String p = "Abc123456";
        Mockito.when(mUser.getPassWord()).thenReturn(p);
        Assert.assertFalse(Login.checkPassInputType(mUser.getPassWord()));
        // Case 2: a-z
        p = "zxcasdqwe";
        Mockito.when(mUser.getPassWord()).thenReturn(p);
        Assert.assertFalse(Login.checkPassInputType(mUser.getPassWord()));
        // Case 3: A-Z
        p = "ZASDSADASD";
        Mockito.when(mUser.getPassWord()).thenReturn(p);
        Assert.assertFalse(Login.checkPassInputType(mUser.getPassWord()));
        // Case 4: 0-9
        p = "1231123456";
        Mockito.when(mUser.getPassWord()).thenReturn(p);
        Assert.assertFalse(Login.checkPassInputType(mUser.getPassWord()));
        // Case 5: A-Z, a-z, 0-9, have 1 space
        p = "ASDasD A123!@#1";
        Mockito.when(mUser.getPassWord()).thenReturn(p);
        Assert.assertFalse(Login.checkPassInputType(mUser.getPassWord()));
        // Case 6: a-z, 0-9, have special char
        p = "zxcas@#@#@dqwe123";
        Mockito.when(mUser.getPassWord()).thenReturn(p);
        Assert.assertFalse(Login.checkPassInputType(mUser.getPassWord()));
        // Case 6: A-Z, a-z, 0-9, have special char
        p = "Axcas@#@#@dqwea";
        Mockito.when(mUser.getPassWord()).thenReturn(p);
        Assert.assertTrue(Login.checkPassInputType(mUser.getPassWord()));
    }

    @Test
    public void checkIsDuplicateUserName() {
        // Case 1: not same
        String s = "zxcasd";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        String p = "abcdef";
        Mockito.when(mUser.getPassWord()).thenReturn(p);
        Assert.assertTrue(Login.checkIsDuplicateUserName(mUser.getPassWord(), mUser.getUserName()));
        // Case 2: same
        s = "dapchaivlahihi";
        Mockito.when(mUser.getUserName()).thenReturn(s);
        p = "dapchaivlahihi";
        Mockito.when(mUser.getPassWord()).thenReturn(p);
        Assert.assertFalse(Login.checkIsDuplicateUserName(mUser.getPassWord(), mUser.getUserName()));
    }

    @Test
    public void checkSpacePassword() {
        // 1 space
        String p = "Hello";
        Mockito.when(mUser.getPassWord()).thenReturn(p);
        Assert.assertTrue(Login.checkSpacePassword(mUser.getPassWord()));
        // case 2
        p = "H e l l o";
        Mockito.when(mUser.getPassWord()).thenReturn(p);
        Assert.assertFalse(Login.checkSpacePassword(mUser.getPassWord()));
        // case 3
        p = "Hello ";
        Mockito.when(mUser.getPassWord()).thenReturn(p);
        Assert.assertFalse(Login.checkSpacePassword(mUser.getPassWord()));
        // case 4
        p = " Hello";
        Mockito.when(mUser.getPassWord()).thenReturn(p);
        Assert.assertFalse(Login.checkSpacePassword(mUser.getPassWord()));
        // case 5
        p = " Hello ";
        Mockito.when(mUser.getPassWord()).thenReturn(p);
        Assert.assertFalse(Login.checkSpacePassword(mUser.getPassWord()));
        // case 6
        p = " H e l l o ";
        Mockito.when(mUser.getPassWord()).thenReturn(p);
        Assert.assertFalse(Login.checkSpacePassword(mUser.getPassWord()));
    }
}

