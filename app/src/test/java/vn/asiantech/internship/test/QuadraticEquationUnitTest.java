package vn.asiantech.internship.test;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.day26.QuadraticEquation;

import static org.mockito.Mockito.when;

/**
 * Created by at-dinhvo on 11/07/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class QuadraticEquationUnitTest {

    @Mock
    private QuadraticEquation mQuadraticEquation;

    @Test
    public void checkDelta() {
        when(mQuadraticEquation.getDelta(3, -4, 1)).thenReturn(4f);
        Assert.assertEquals(mQuadraticEquation.getDelta(3, -4, 1), (-4) * (-4) - 4 * 3 * 1f);
    }

    @Test
    public void checkResult() {
        when(mQuadraticEquation.getResult(3, -4, 1)).thenReturn("X1 = " + 1f
                + ", X2 = " + (float) 1 / 3);
        Assert.assertEquals(mQuadraticEquation.getResult(3, -4, 1), "X1 = " + 1f
                + ", X2 = " + (float) 1 / 3);
    }
}
