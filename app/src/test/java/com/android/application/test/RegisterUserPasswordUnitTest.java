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
public class RegisterUserPasswordUnitTest {

    private static final String USER_NAME = "Thien";
    private static final String[] PASSWORDS = {"nguyenthanhPass12(", "_DayLapassDung98", "ThisISpass_98"};
    private static final String[] PASSWORDS_PAIL = {"ThanhThien97(", "_thienNguyen123", "Thien9753_Thanh", "thien090_@#$_Nguyen"};
    @Mock
    private UserTest mUserTest;

    @Test
    public void passwordHasUser() {
        Mockito.when(mUserTest.getUserName()).thenReturn(USER_NAME);
        for (String password : PASSWORDS) {
            Assert.assertTrue(RegisterChecker.hasUser(password, mUserTest.getUserName()));
        }
    }

    @Test
    public void passwordHasUserFalse() {
        Mockito.when(mUserTest.getUserName()).thenReturn(USER_NAME);
        for (String passwordFail : PASSWORDS_PAIL) {
            Assert.assertFalse(RegisterChecker.hasUser(passwordFail, mUserTest.getUserName()));
        }
    }
}
