package vn.asiantech.internship.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.ui.unittest.EquationTest;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 *
 * Created by quanghai on 11/07/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class EquationUnitTest {
    @Mock
    private EquationTest mEquation;

    @Test
    public void checkDelta() {
        when(mEquation.getDelta(1, 2, -1)).thenReturn(8f);
        assertEquals(mEquation.getDelta(1, 2, -1), (float) (2 * 2 - 4 * (-1)));
    }

    @Test
    public void checkConditionNoRoot() {
        when(mEquation.getRoot(1, 2, 2)).thenReturn("No root");
        assertEquals(mEquation.getRoot(1, 2, 2), "No root");
    }

    @Test
    public void checkConditionSingleRoot() {
        when(mEquation.getRoot(1, 2, 1)).thenReturn("x = -1");
        assertEquals(mEquation.getRoot(1, 2, 1), "x = -1");
    }

    @Test
    public void checkConditionTwoRoot() {
        when(mEquation.getRoot(1, 0, -1)).thenReturn("x1 = 1, x2 = -1");
        assertEquals(mEquation.getRoot(1, 0, -1), "x1 = 1, x2 = -1");
    }
}
