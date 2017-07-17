package vn.asiantech.internship.test;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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

    private QuadraticEquation mQuadraticEquation = new QuadraticEquation();

    @Test
    public void checkDelta() {
        when(mParameter.getDelta()).thenReturn(4f);
        Assert.assertEquals(mParameter.getDelta(), (-4) * (-4) - 4 * 3 * 1f);
    }

    /*@Test
    public void checkDelta01(){
        when(mParameter.getA()).thenReturn(3f);
        when(mParameter.getB()).thenReturn(-4f);
        when(mParameter.getC()).thenReturn(1f);
        System.out.println(mParameter.getA());
        System.out.println(mParameter.getB());
        System.out.println(mParameter.getC());
        when(mQuadraticEquation.getDelta(mParameter.getA(), mParameter.getB(), mParameter.getC())).thenReturn(4f);
        Assert.assertEquals(mQuadraticEquation.getDelta(mParameter.getA(), mParameter.getB(), mParameter.getC()), (-4) * (-4) - 4 * 3 * 1f);
    }*/

    @Test
    public void checkResult() {
//        when(mParameter.getA()).thenReturn(3f);
//        when(mParameter.getB()).thenReturn(-4f);
//        when(mParameter.getC()).thenReturn(1f);
//        System.out.println(mParameter.getA());
//        System.out.println(mParameter.getB());
//        System.out.println(mParameter.getC());
        when(mQuadraticEquation.getResult(new Parameter(mParameter.getA(), mParameter.getB(), mParameter.getC()))).thenReturn("X1 = " + 1f
                + ", X2 = " + (float) 1 / 3);
//        System.out.println(mQuadraticEquation.getResult(mParameter));
        Assert.assertEquals(mQuadraticEquation.getResult(new Parameter(mParameter.getA(), mParameter.getB(), mParameter.getC())), "X1 = " + 1f
                + ", X2 = " + (float) 1 / 3);
    }
}
