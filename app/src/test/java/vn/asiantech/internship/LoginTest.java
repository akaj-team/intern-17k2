package vn.asiantech.internship;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.exday26.Login;

/**
 * Created by datbu on 12-07-2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginTest {

    @Test
    public void checkUserLength() {
        // Case 1: Length < 6
        String s = "hello";
        Assert.assertFalse(Login.checkLengthUsername(s));
        // Case 2: Length = 6
        s = "helloa";
        Assert.assertTrue(Login.checkLengthUsername(s));
        // Case 3: 6 < Length < 24
        s = "helloHello";
        Assert.assertTrue(Login.checkLengthUsername(s));
        // Case 4: Length > 24
        s = "hello123456789123456789hello123";
        Assert.assertFalse(Login.checkLengthUsername(s));
        // Case 5: Length = 24
        s = "hello1234567890123456789";
        Assert.assertTrue(Login.checkLengthUsername(s));
    }

    @Test
    public void checkSpaceUserName() {
        //  case 1
        String s = "Hello";
        Assert.assertTrue(Login.checkSpaceUserName(s));
        // case 2
        s = "H e l l o";
        Assert.assertFalse(Login.checkSpaceUserName(s));
        // case 3
        s = "Hello ";
        Assert.assertFalse(Login.checkSpaceUserName(s));
        // case 4
        s = " Hello";
        Assert.assertFalse(Login.checkSpaceUserName(s));
        // case 5
        s = " Hello ";
        Assert.assertFalse(Login.checkSpaceUserName(s));
        // case 6
        s = " H e l l o ";
        Assert.assertFalse(Login.checkSpaceUserName(s));
    }

    @Test
    public void checkUserNameAlphaNumeric() {
        // Case 1: A-Z, a-z, 0-9
        String s = "Abc123456";
        Assert.assertTrue(Login.checkAlphaNumeric(s));
        // Case 2: a-z
        s = "zxcasdqwe";
        Assert.assertTrue(Login.checkAlphaNumeric(s));
        // Case 3: A-Z
        s = "ZASDSADASD";
        Assert.assertTrue(Login.checkAlphaNumeric(s));
        // Case 4: 0-9
        s = "1231123456";
        Assert.assertTrue(Login.checkAlphaNumeric(s));
        // Case 5: A-Z, a-z, 0-9, have 1 space
        s = "ASDasD A1231";
        Assert.assertFalse(Login.checkAlphaNumeric(s));
        // Case 6: A-Z, a-z, 0-9, have special char
        s = "zxcas123@#@#@dqwe";
        Assert.assertFalse(Login.checkAlphaNumeric(s));
    }

    @Test
    public void checkUserNameChar() {
        // Case 1: have upper and lower case
        String s = "AbcdE";
        Assert.assertTrue(Login.checkUpperCase(s));
        // Case 2: just have lower case
        s = "asdzxcqwe";
        Assert.assertTrue(Login.checkUpperCase(s));
        // Case 3 : just have upper case
        s = "ASDZXCQWE";
        Assert.assertTrue(Login.checkUpperCase(s));
        // Case 4: have upper and lower case, have space
        s = "ASD zxc";
        Assert.assertFalse(Login.checkUpperCase(s) && Login.checkSpaceUserName(s));
        // Case 5: have upper and lower case, have special char
        s = "ASDzxc!@#";
        Assert.assertFalse(Login.checkUpperCase(s) && Login.checkAlphaNumeric(s));
        // Case 6: have upper and lower case, space, special char
        s = "ASD zxc!@#";
        Assert.assertFalse(Login.checkUpperCase(s) && Login.checkSpaceUserName(s) && Login.checkAlphaNumeric(s));
        // Case 7: have lower case, space, special char
        s = "asd zxc @!#";
        Assert.assertFalse(Login.checkUpperCase(s) && Login.checkSpaceUserName(s) && Login.checkAlphaNumeric(s));
    }

    @Test
    public void checkPassLength() {
        // Case 1: Length > 4
        String p = "hel";
        Assert.assertFalse(Login.checkPassLength(p));
        // Case 2: Length = 4
        p = "hell";
        Assert.assertTrue(Login.checkPassLength(p));
        // Case 3: 4 < Length
        p = "helloHello";
        Assert.assertTrue(Login.checkPassLength(p));
    }

    @Test
    public void checkPassInputType() {
        // Case 1: A-Z, a-z, 0-9
        String p = "Abc123456";
        Assert.assertFalse(Login.checkPassInputType(p));
        // Case 2: a-z
        p = "zxcasdqwe";
        Assert.assertFalse(Login.checkPassInputType(p));
        // Case 3: A-Z
        p = "ZASDSADASD";
        Assert.assertFalse(Login.checkPassInputType(p));
        // Case 4: 0-9
        p = "1231123456";
        Assert.assertFalse(Login.checkPassInputType(p));
        // Case 5: A-Z, a-z, 0-9, have 1 space
        p = "ASDasD A123!@#1";
        Assert.assertFalse(Login.checkPassInputType(p));
        // Case 6: a-z, 0-9, have special char
        p = "zxcas@#@#@dqwe123";
        Assert.assertFalse(Login.checkPassInputType(p));
        // Case 6: A-Z, a-z, 0-9, have special char
        p = "Axcas@#@#@dqwea";
        Assert.assertTrue(Login.checkPassInputType(p));
    }

    @Test
    public void checkIsDuplicateUserName() {
        // Case 1: not same
        String s = "zxcasd";
        String p = "abcdef";
        Assert.assertTrue(Login.checkIsDuplicateUserName(p, s));
        // Case 2: same
        s = "dapchaivlahihi";
        p = "dapchaivlahihi";
        Assert.assertFalse(Login.checkIsDuplicateUserName(p, s));
    }

    @Test
    public void checkSpacePassword() {
        // 1 space
        String p = "Hello";
        Assert.assertTrue(Login.checkSpacePassword(p));
        // case 2
        p = "H e l l o";
        Assert.assertFalse(Login.checkSpacePassword(p));
        // case 3
        p = "Hello ";
        Assert.assertFalse(Login.checkSpacePassword(p));
        // case 4
        p = " Hello";
        Assert.assertFalse(Login.checkSpacePassword(p));
        // case 5
        p = " Hello ";
        Assert.assertFalse(Login.checkSpacePassword(p));
        // case 6
        p = " H e l l o ";
        Assert.assertFalse(Login.checkSpacePassword(p));
    }
}

