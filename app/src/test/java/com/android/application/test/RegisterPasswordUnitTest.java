package com.android.application.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.ui.testcase.RegisterChecker;
import vn.asiantech.internship.ui.testcase.User;

/**
 * Created by Thanh Thien on 7/10/2017.
 * User
 */
@RunWith(MockitoJUnitRunner.class)
public class RegisterPasswordUnitTest {
    private static final int MIN_PASS_LENGTH = 4;

    @Mock
    private User mUser;
    private Utils mUtils = new Utils();
    private RegisterChecker mRegisterChecker = new RegisterChecker();

    @Test
    public void checkMinLengthPassword() {
        for (String trueNameCheckLength : mUtils.trueAllThings) {
            Mockito.when(mUser.getPassWord()).thenReturn(trueNameCheckLength);
            Assert.assertTrue(mRegisterChecker.checkMinLength(mUser.getPassWord(), MIN_PASS_LENGTH));
        }
    }

    @Test
    public void checkMinLengthPasswordFalse() {
        for (String falseLengthPassword : mUtils.falseLengthPasswords) {
            Mockito.when(mUser.getPassWord()).thenReturn(falseLengthPassword);
            Assert.assertFalse(mRegisterChecker.checkMinLength(mUser.getPassWord(), MIN_PASS_LENGTH));
        }
    }

    @Test
    public void checkSpace() {
        for (String nameTrueAll : mUtils.trueAllThings) {
            Mockito.when(mUser.getPassWord()).thenReturn(nameTrueAll);
            Assert.assertTrue(mRegisterChecker.checkSpace(mUser.getPassWord()));
        }
    }

    @Test
    public void checkHasSymbols() {
        for (String falseNameCheckSymbol : mUtils.falseCheckSymbols) {
            Mockito.when(mUser.getPassWord()).thenReturn(falseNameCheckSymbol);
            Assert.assertTrue(mRegisterChecker.hasSymbol(mUser.getPassWord()));
        }
    }

    @Test
    public void checkHasSymbolsFalse() {
        for (String nameCheckSymbol : mUtils.checkSymbols) {
            Mockito.when(mUser.getPassWord()).thenReturn(nameCheckSymbol);
            Assert.assertFalse(mRegisterChecker.hasSymbol(mUser.getPassWord()));
        }
    }

    @Test
    public void checkHasNumber() {
        for (String trueHasNumber : mUtils.trueHasNumbers) {
            Mockito.when(mUser.getPassWord()).thenReturn(trueHasNumber);
            Assert.assertTrue(mRegisterChecker.hasNumber(mUser.getPassWord()));
        }
    }

    @Test
    public void checkHasNumberFalse() {
        for (String falseAllThing : mUtils.falseAllThings) {
            Mockito.when(mUser.getPassWord()).thenReturn(falseAllThing);
            Assert.assertFalse(mRegisterChecker.hasNumber(mUser.getPassWord()));
        }
    }

    @Test
    public void checkHasUpChar() {
        for (String trueAllPasswordThing : mUtils.trueAllPasswordThings) {
            Mockito.when(mUser.getPassWord()).thenReturn(trueAllPasswordThing);
            Assert.assertTrue(mRegisterChecker.hasUpChar(mUser.getPassWord()));
        }
    }

    @Test
    public void checkHasUpCharFalse() {
        for (String falseUpChar : mUtils.falseUpChars) {
            Mockito.when(mUser.getPassWord()).thenReturn(falseUpChar);
            Assert.assertFalse(mRegisterChecker.hasUpChar(mUser.getPassWord()));
        }
    }
}
