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
    private Login mLogin = new Login();

    @Mock
    private UserTest mUser;

    @Test
    public void checkMinLengthUserName() {
        when(mUser.getUserName()).thenReturn("xxx");
        assertFalse(mLogin.getMinLengthUserName(mUser.getUserName()));
    }

    @Test
    public void checkMaxLengthUserName() {
        when(mUser.getUserName()).thenReturn("nregeg");
        assertTrue(mLogin.getMaxLengthUserName(mUser.getUserName()));
    }

    @Test
    public void checkSpaceUserName() {
        when(mUser.getUserName()).thenReturn("gegk tt");
        assertTrue(mLogin.getSpace(mUser.getUserName()));
    }

    @Test
    public void checkAlphaNumbericAndIpLowCase() {
        when(mUser.getUserName()).thenReturn("jnirg&*(*");
        assertFalse(mLogin.getAlphaNumberic(mUser.getUserName()));
    }

    @Test
    public void checkLengthPassword() {
        when(mUser.getPassword()).thenReturn("ahiahai");
        assertTrue(mLogin.getLengthPassword(mUser.getPassword()));
    }

    @Test
    public void checkPassword() {
        when(mUser.getUserName()).thenReturn("zxczxc");
        when(mUser.getPassword()).thenReturn("zxczxc");
        assertFalse(mLogin.getPassword(mUser.getPassword(), mUser.getUserName()));
    }

    @Test
    public void checkPasswordNumber() {
        when(mUser.getPassword()).thenReturn("hai123");
        assertTrue(mLogin.getPasswordContainNumber(mUser.getPassword()));
    }

    @Test
    public void checkPasswordUpcase() {
        when(mUser.getPassword()).thenReturn("Haiht");
        assertTrue(mLogin.getPasswordContainUpCase(mUser.getPassword()));
    }

    @Test
    public void checkPasswordSpecialChar() {
        when(mUser.getPassword()).thenReturn("Hoj1(**");
        assertTrue(mLogin.getPasswordContainSpecialChar(mUser.getPassword()));
    }

    @Test
    public void checkPasswordSpace() {
        when(mUser.getPassword()).thenReturn("nf   t");
        assertFalse(mLogin.getPasswordSpace(mUser.getPassword()));
    }
}
