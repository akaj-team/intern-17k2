package vn.asiantech.internship.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import vn.asiantech.internship.models.UserTest;
import vn.asiantech.internship.ui.unittest.Login;

import static org.mockito.Mockito.when;

/**
 *
 * Created by quanghai on 11/07/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserUnitTest {
    @Mock
    private UserTest mUser;

    @Test
    public void checkMinLengthUserName() {
        when(mUser.getUserName()).thenReturn("xxx");
        assertFalse(Login.getMinLengthUserName(mUser.getUserName()));
    }

    @Test
    public void checkMaxLengthUserName() {
        when(mUser.getUserName()).thenReturn("nregeg");
        assertTrue(Login.getMaxLengthUserName(mUser.getUserName()));
    }

    @Test
    public void checkSpaceUserName() {
        when(mUser.getUserName()).thenReturn("gegk tt");
        assertTrue(Login.getSpace(mUser.getUserName()));
    }

    @Test
    public void checkAlphaNumbericAndIpLowCase() {
        when(mUser.getUserName()).thenReturn("jnirg&*(*");
        assertFalse(Login.getAlphaNumberic(mUser.getUserName()));
    }

    @Test
    public void checkLengthPassword() {
        when(mUser.getPassword()).thenReturn("ahiahai");
        assertTrue(Login.getLengthPassword(mUser.getPassword()));
    }

    @Test
    public void checkPassword() {
        when(mUser.getUserName()).thenReturn("zxczxc");
        when(mUser.getPassword()).thenReturn("zxczxc");
        assertFalse(Login.getPassword(mUser.getPassword(), mUser.getUserName()));
    }

    @Test
    public void checkPasswordNumber() {
        when(mUser.getPassword()).thenReturn("hai123");
        assertTrue(Login.getPasswordContainNumber(mUser.getPassword()));
    }

    @Test
    public void checkPasswordUpcase() {
        when(mUser.getPassword()).thenReturn("Haiht");
        assertTrue(Login.getPasswordContainUpCase(mUser.getPassword()));
    }

    @Test
    public void checkPasswordSpecialChar() {
        when(mUser.getPassword()).thenReturn("Hoj1(**");
        assertTrue(Login.getPasswordContainSpecialChar(mUser.getPassword()));
    }

    @Test
    public void checkPasswordSpace() {
        when(mUser.getPassword()).thenReturn("nf   t");
        assertFalse(Login.getPasswordSpace(mUser.getPassword()));
    }
}
