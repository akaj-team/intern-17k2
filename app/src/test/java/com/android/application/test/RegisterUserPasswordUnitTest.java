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
 * Copyright © 2017 AsianTech inc.
 *
 * @author Thanh Thien
 *         Created on 7/12/2017
 */
@RunWith(MockitoJUnitRunner.class)
public class RegisterUserPasswordUnitTest {

    private static final String USER_NAME = "Thien";
    private static final String[] PASSWORDS = {"nguyenthanhPass12(", "_DayLapassDung98", "ThisISpass_98"};
    private static final String[] PASSWORDS_FAILS = {"ThanhThien97(", "_thienNguyen123", "Thien9753_Thanh", "thien090_@#$_Nguyen"};
    @Mock
    private User mUser;
    private RegisterChecker mRegisterChecker = new RegisterChecker();

    @Test
    public void passwordHasUser() {
        Mockito.when(mUser.getUserName()).thenReturn(USER_NAME);
        for (String password : PASSWORDS) {
            Assert.assertTrue(mRegisterChecker.hasUser(password, mUser.getUserName()));
        }
    }

    @Test
    public void passwordHasUserFalse() {
        Mockito.when(mUser.getUserName()).thenReturn(USER_NAME);
        for (String passwordFail : PASSWORDS_FAILS) {
            Assert.assertFalse(mRegisterChecker.hasUser(passwordFail, mUser.getUserName()));
        }
    }
}
