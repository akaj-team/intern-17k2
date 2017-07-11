package com.android.application.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.ui.testcase.RegisterChecker;
import vn.asiantech.internship.ui.testcase.UserTest;

/**
 * Created by Thanh Thien on 7/10/2017.
 * UserTest
 */
@RunWith(MockitoJUnitRunner.class)
public class RegisterPasswordUnitTest {
    private static final int MIN_PASS_LENGTH = 4;

    @Mock
    private UserTest mUserTest;
    private Utils mUtils = new Utils();
    private RegisterChecker mRegisterChecker = new RegisterChecker();

    @Test
    public void checkMinLengthPassword() {
        for (String trueNameCheckLength : mUtils.trueAllThings) {
            Mockito.when(mUserTest.getPassWord()).thenReturn(trueNameCheckLength);
            Assert.assertTrue(mRegisterChecker.checkMinLength(mUserTest.getPassWord(), MIN_PASS_LENGTH));
        }
    }

    @Test
    public void checkMinLengthPasswordFalse() {
        for (String falseLengthPassword : mUtils.falseLengthPasswords) {
            Mockito.when(mUserTest.getPassWord()).thenReturn(falseLengthPassword);
            Assert.assertFalse(mRegisterChecker.checkMinLength(mUserTest.getPassWord(), MIN_PASS_LENGTH));
        }
    }

    @Test
    public void checkSpace() {
        for (String nameTrueAll : mUtils.trueAllThings) {
            Mockito.when(mUserTest.getPassWord()).thenReturn(nameTrueAll);
            Assert.assertTrue(mRegisterChecker.checkSpace(mUserTest.getPassWord()));
        }
    }

    @Test
    public void checkHasSymbols() {
        for (String falseNameCheckSymbol : mUtils.falseCheckSymbols) {
            Mockito.when(mUserTest.getPassWord()).thenReturn(falseNameCheckSymbol);
            Assert.assertTrue(mRegisterChecker.hasSymbol(mUserTest.getPassWord()));
        }
    }

    @Test
    public void checkHasSymbolsFalse() {
        for (String nameCheckSymbol : mUtils.checkSymbols) {
            Mockito.when(mUserTest.getPassWord()).thenReturn(nameCheckSymbol);
            Assert.assertFalse(mRegisterChecker.hasSymbol(mUserTest.getPassWord()));
        }
    }

    @Test
    public void checkHasNumber() {
        for (String trueHasNumber : mUtils.trueHasNumbers) {
            Mockito.when(mUserTest.getPassWord()).thenReturn(trueHasNumber);
            Assert.assertTrue(mRegisterChecker.hasNumber(mUserTest.getPassWord()));
        }
    }

    @Test
    public void checkHasNumberFalse() {
        for (String falseAllThing : mUtils.falseAllThings) {
            Mockito.when(mUserTest.getPassWord()).thenReturn(falseAllThing);
            Assert.assertFalse(mRegisterChecker.hasNumber(mUserTest.getPassWord()));
        }
    }

    @Test
    public void checkHasUpChar() {
        for (String trueAllPasswordThing : mUtils.trueAllPasswordThings) {
            Mockito.when(mUserTest.getPassWord()).thenReturn(trueAllPasswordThing);
            Assert.assertTrue(mRegisterChecker.hasUpChar(mUserTest.getPassWord()));
        }
    }

    @Test
    public void checkHasUpCharFalse() {
        for (String falseUpChar : mUtils.falseUpChars) {
            Mockito.when(mUserTest.getPassWord()).thenReturn(falseUpChar);
            Assert.assertFalse(mRegisterChecker.hasUpChar(mUserTest.getPassWord()));
        }
    }
}
