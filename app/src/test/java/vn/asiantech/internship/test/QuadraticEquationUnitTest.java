package vn.asiantech.internship.test;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.day26.Parameter;
import vn.asiantech.internship.day26.QuadraticEquation;

import static org.mockito.Mockito.when;

/**
 * Created by at-dinhvo on 11/07/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class QuadraticEquationUnitTest {

    @Mock
    private Parameter mParameter;

    @Spy
    private QuadraticEquation mQuadraticEquation;

    @Test
    public void checkDelta() {
        when(mParameter.getA()).thenReturn(3f);
        when(mParameter.getB()).thenReturn(-4f);
        when(mParameter.getC()).thenReturn(1f);
        System.out.print(mQuadraticEquation.getDelta(mParameter.getA(), mParameter.getB(), mParameter.getC()));
        when(mQuadraticEquation.getDelta(mParameter.getA(), mParameter.getB(), mParameter.getC()))
                .thenReturn(4f);
        Assert.assertEquals(mQuadraticEquation.getDelta(mParameter.getA(), mParameter.getB(), mParameter.getC()),
                (-4) * (-4) - 4 * 3 * 1f);
    }

    @Test
    public void checkResult() {
        when(mParameter.getA()).thenReturn(3f);
        when(mParameter.getB()).thenReturn(-4f);
        when(mParameter.getC()).thenReturn(1f);
        when(mQuadraticEquation.getResult(mParameter)).thenReturn("X1 = " + 1f
                + ", X2 = " + (float) 1 / 3);
        Assert.assertEquals(mQuadraticEquation.getResult(mParameter), "X1 = " + 1f
                + ", X2 = " + (float) 1 / 3);
    }
}
