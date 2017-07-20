package vn.asiantech.internship;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.exday26.Equation;
import vn.asiantech.internship.exday26.QuadraticEquationTest;

import static org.mockito.Mockito.when;

/**
 * Created by datbu on 13-07-2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class QuadraticTest {

    @Mock
    private Equation mEquation;

    @Test
    public void checkInput() {
        // Case 1: a!= 0
        int a = 5;
        int b = 1;
        int c = -4;
        when(mEquation.getDelta()).thenReturn(81.0);
        when(mEquation.getCheckInput()).thenReturn("x1 = 0.8", "x2 = -1");
        Assert.assertEquals(QuadraticEquationTest.getDelta(a, b, c), mEquation.getDelta(), 81.0);
        Assert.assertEquals(QuadraticEquationTest.checkInput(a, b, c), mEquation.getCheckInput(), "x1 = 0.8");
        Assert.assertEquals(QuadraticEquationTest.checkInput(a, b, c), mEquation.getCheckInput(), "x2 = -1");
        // Case 2: a = 0, b = 0, c = 0
        a = 0;
        b = 0;
        c = 0;
        when(mEquation.getDelta()).thenReturn(0.0);
        when(mEquation.getCheckInput()).thenReturn("Countless solutions");
        Assert.assertEquals(QuadraticEquationTest.getDelta(a, b, c), mEquation.getDelta(), 0.0);
        Assert.assertEquals(QuadraticEquationTest.checkInput(a, b, c), mEquation.getCheckInput(), "Countless solutions");
        // Case 3: a = 0, b = 0, c != 0
        a = 0;
        b = 0;
        c = 1;
        when(mEquation.getDelta()).thenReturn(4.0);
        when(mEquation.getCheckInput()).thenReturn("Error");
        Assert.assertEquals(QuadraticEquationTest.getDelta(a, b, c), mEquation.getDelta(), 4.0);
        Assert.assertEquals(QuadraticEquationTest.checkInput(a, b, c), mEquation.getCheckInput(), "Error");
        // Case 4: a = 0, b != 0, c != 0
        a = 0;
        b = 2;
        c = -2;
        when(mEquation.getDelta()).thenReturn(12.0);
        when(mEquation.getCheckInput()).thenReturn("x = 1");
        Assert.assertEquals(QuadraticEquationTest.getDelta(a, b, c), mEquation.getDelta(), 12.0);
        Assert.assertEquals(QuadraticEquationTest.checkInput(a, b, c), mEquation.getCheckInput(), "x = 1");
        // Case 5: a = 0, b != 0, c = 0
        a = 0;
        b = 5;
        c = 0;
        when(mEquation.getDelta()).thenReturn(25.0);
        when(mEquation.getCheckInput()).thenReturn("x = 0");
        Assert.assertEquals(QuadraticEquationTest.getDelta(a, b, c), mEquation.getDelta(), 25.0);
        Assert.assertEquals(QuadraticEquationTest.checkInput(a, b, c), mEquation.getCheckInput(), "x = 0");
        // Case 6: a!= 0
        a = -2;
        b = 4;
        c = 0;
        when(mEquation.getDelta()).thenReturn(16.0);
        when(mEquation.getCheckInput()).thenReturn("x1 = 0", "x2 = 2");
        Assert.assertEquals(QuadraticEquationTest.getDelta(a, b, c), mEquation.getDelta(), 16.0);
        Assert.assertEquals(QuadraticEquationTest.checkInput(a, b, c), mEquation.getCheckInput(), "x1 = 0");
        Assert.assertEquals(QuadraticEquationTest.checkInput(a, b, c), mEquation.getCheckInput(), "x2 = 2");
        // Case 7: a!= 0
        a = 1;
        b = 2;
        c = 1;
        when(mEquation.getDelta()).thenReturn(0.0);
        when(mEquation.getCheckInput()).thenReturn("x = -1");
        Assert.assertEquals(QuadraticEquationTest.getDelta(a, b, c), mEquation.getDelta(), 0.0);
        Assert.assertEquals(QuadraticEquationTest.checkInput(a, b, c), mEquation.getCheckInput(), "x = -1");
    }
}
