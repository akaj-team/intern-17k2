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
public class RegisterUserUnitTest {
    private static final int MIN_USER_LENGTH = 6;
    private static final int MAX_USER_LENGTH = 26;

    @Mock
    private UserTest mUserTest;
    private Utils mUtils = new Utils();
    private RegisterChecker mRegisterChecker = new RegisterChecker();

    @Test
    public void checkMinLengthUser() {
        for (String trueNameCheckLength : mUtils.trueAllThings) {
            Mockito.when(mUserTest.getUserName()).thenReturn(trueNameCheckLength);
            Assert.assertTrue(mRegisterChecker.checkMinLength(mUserTest.getUserName(), MIN_USER_LENGTH));
        }
    }

    @Test
    public void checkMinLengthUserFalse() {
        for (String falseNameCheckLength : mUtils.falseAllThings) {
            Mockito.when(mUserTest.getUserName()).thenReturn(falseNameCheckLength);
            Assert.assertFalse(mRegisterChecker.checkMinLength(mUserTest.getUserName(), MIN_USER_LENGTH));
        }
    }

    @Test
    public void checkMaxLengthUser() {
        for (String trueNameCheckMaxLength : mUtils.trueCheckMaxLengths) {
            Mockito.when(mUserTest.getUserName()).thenReturn(trueNameCheckMaxLength);
            Assert.assertTrue(mRegisterChecker.checkMaxLength(mUserTest.getUserName(), MAX_USER_LENGTH));
        }
    }

    @Test
    public void checkMaxLengthUserFalse() {
        for (String falseNameCheckMaxLength : mUtils.falseCheckMaxLengths) {
            Mockito.when(mUserTest.getUserName()).thenReturn(falseNameCheckMaxLength);
            Assert.assertFalse(mRegisterChecker.checkMaxLength(mUserTest.getUserName(), MAX_USER_LENGTH));
        }
    }

    @Test
    public void checkOnlyAlphabet() {
        for (String trueAllThing : mUtils.trueUserName) {
            Mockito.when(mUserTest.getUserName()).thenReturn(trueAllThing);
            Assert.assertTrue(mRegisterChecker.trueAllUserName(mUserTest.getUserName()));
        }
    }

    @Test
    public void checkOnlyAlphabetFalse() {
        for (String falseCheckAlphabet : mUtils.falseCheckAlphabets) {
            Mockito.when(mUserTest.getUserName()).thenReturn(falseCheckAlphabet);
            Assert.assertFalse(mRegisterChecker.trueAllUserName(mUserTest.getUserName()));
        }
    }
}
