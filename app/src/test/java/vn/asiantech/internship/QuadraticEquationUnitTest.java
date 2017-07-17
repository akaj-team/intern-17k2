package vn.asiantech.internship;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.unittest.QuadraticEquationTest;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.when;

/**
 * Created by ducle on 16/07/2017.
 * QuadraticEquationUnitTest to test logic in QuadraticEquationTest
 */
@RunWith(MockitoJUnitRunner.class)
public class QuadraticEquationUnitTest {

    @Mock
    private QuadraticEquationTest mQuadraticEquationTest;

    @Test
    public void getDeltaTest() {
        double a = 1;
        double b = 3;
        double c = 2;
        when(mQuadraticEquationTest.getDelta(a, b, c)).thenReturn((double) 1);
        assertEquals(1, mQuadraticEquationTest.getDelta(a, b, c), 0.001);

        a = 2;
        b = 4;
        c = 2;
        when(mQuadraticEquationTest.getDelta(a, b, c)).thenReturn((double) 0);
        assertEquals(0, mQuadraticEquationTest.getDelta(a, b, c), 0.001);

        a = 3;
        b = 1;
        c = 2;
        when(mQuadraticEquationTest.getDelta(a, b, c)).thenReturn((double) -23);
        assertEquals(-23, mQuadraticEquationTest.getDelta(a, b, c), 0.001);
    }

    @Test
    public void getResultTest() {
        double[] result = new double[2];

        //check case a = 0, b = 0
        double a = 0;
        double b = 0;
        double c = 1;
        when(mQuadraticEquationTest.getResult(a, b, c)).thenReturn(null);
        assertNull(mQuadraticEquationTest.getResult(a, b, c));

        //check case delta < 0
        a = 3;
        b = 2;
        c = 1;
        when(mQuadraticEquationTest.getResult(a, b, c)).thenReturn(null);
        assertNull(mQuadraticEquationTest.getResult(a, b, c));

        //check case a = 0, b != 0
        a = 0;
        b = 1;
        c = 2;
        result[0] = 2;
        when(mQuadraticEquationTest.getResult(a, b, c)).thenReturn(result);
        assertEquals(result, mQuadraticEquationTest.getResult(a, b, c));

        //check case delta = 0
        a = 2;
        b = 4;
        c = 2;
        result[0] = -1;
        when(mQuadraticEquationTest.getResult(a, b, c)).thenReturn(result);
        assertEquals(result, mQuadraticEquationTest.getResult(a, b, c));

        //check case delta > 0
        a = 1;
        b = -3;
        c = 2;
        result[0] = 1;
        result[1] = 2;
        when(mQuadraticEquationTest.getResult(a, b, c)).thenReturn(result);
        assertEquals(result, mQuadraticEquationTest.getResult(a, b, c));
    }
}
