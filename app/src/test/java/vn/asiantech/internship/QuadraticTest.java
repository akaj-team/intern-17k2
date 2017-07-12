package vn.asiantech.internship;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import vn.asiantech.internship.exday26.QuadraticEquationTest;

import static org.mockito.Mockito.when;

/**
 * Created by datbu on 13-07-2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class QuadraticTest {

    @Mock
    private QuadraticEquationTest mQuadraticEquationTest;

    @Test
    public void checkInput() {
        // Case 1: a!= 0
        int a = 5;
        int b = 1;
        int c = -4;
        when(mQuadraticEquationTest.checkInput(a, b, c)).thenReturn("x1 = 0.8", "x2 = -1");
        Assert.assertEquals(mQuadraticEquationTest.checkInput(a, b, c), "x1 = 0.8");
        Assert.assertEquals(mQuadraticEquationTest.checkInput(a, b, c), "x2 = -1");
        // Case 2: a = 0, b = 0, c = 0
        a = 0;
        b = 0;
        c = 0;
        when(mQuadraticEquationTest.checkInput(a, b, c)).thenReturn("Countless solutions");
        Assert.assertEquals(mQuadraticEquationTest.checkInput(a, b, c), "Countless solutions");
        // Case 3: a = 0, b = 0, c != 0
        a = 0;
        b = 0;
        c = 1;
        when(mQuadraticEquationTest.checkInput(a, b, c)).thenReturn("Error");
        Assert.assertEquals(mQuadraticEquationTest.checkInput(a, b, c), "Error");
        // Case 4: a = 0, b != 0, c != 0
        a = 0;
        b = 2;
        c = -2;
        when(mQuadraticEquationTest.checkInput(a, b, c)).thenReturn("x = 1");
        Assert.assertEquals(mQuadraticEquationTest.checkInput(a, b, c), "x = 1");
        // Case 5: a = 0, b != 0, c = 0
        a = 0;
        b = 5;
        c = 0;
        when(mQuadraticEquationTest.checkInput(a, b, c)).thenReturn("x = 0");
        Assert.assertEquals(mQuadraticEquationTest.checkInput(a, b, c), "x = 0");
        // Case 6: a!= 0
        a = 2;
        b = 0;
        c = -2;
        when(mQuadraticEquationTest.checkInput(a, b, c)).thenReturn("x1 = 1", "x2 = -1");
        Assert.assertEquals(mQuadraticEquationTest.checkInput(a, b, c), "x1 = 1");
        Assert.assertEquals(mQuadraticEquationTest.checkInput(a, b, c), "x2 = -1");
        // Case 7: a!= 0
        a = 1;
        b = 2;
        c = 1;
        when(mQuadraticEquationTest.checkInput(a, b, c)).thenReturn("x = -1");
        Assert.assertEquals(mQuadraticEquationTest.checkInput(a, b, c), "x = -1");
    }
}
